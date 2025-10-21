package org.hbrs.ia.code;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import static com.mongodb.client.model.Filters.eq;


abstract class ManagePersonalAbstract implements ManagePersonal {

    private final MongoClient client;
    private final MongoCollection<Document> salesmenCollection;
    private static final String DATABASE_NAME = "Database";
    private static final String SALESMEN_COLLECTION_NAME = "salesmen";

    public ManagePersonalAbstract(String connectinoString) {
        this.client = MongoClients.create(connectinoString);
        this.salesmenCollection = this.client.getDatabase(DATABASE_NAME).getCollection(SALESMEN_COLLECTION_NAME);
    }

    private SocialPerformanceRecord documentToPerformanceRecord(Document doc) {

        return new SocialPerformanceRecord(
                doc.getInteger("id"),
                doc.getInteger("salesman"),
                doc.getInteger("year"),
                doc.getInteger("leadershipCompetence"),
                doc.getInteger("openessEmployees"),
                doc.getInteger("socialBehaviour"),
                doc.getInteger("attitudeClient"),
                doc.getInteger("communication"),
                doc.getInteger("integrity")
        );
    }

    private SalesMan documentToSalesMan(Document doc) {

        List<Document> performanceDocs = doc.getList("performanceRecords", Document.class);

        List<SocialPerformanceRecord> performanceRecords = performanceDocs.stream()
                .map(this::documentToPerformanceRecord)
                .collect(Collectors.toList());

        return new SalesMan(
                doc.getString("firstname"),
                doc.getString("lastname"),
                doc.getInteger("sid"),
                performanceRecords
        );
    }

    private boolean salesManExists(int sid){
        return salesmenCollection.find(eq("sid", sid)).first() != null;
    }

    private boolean recordExists(int sid, SocialPerformanceRecord record){
        SalesMan s = readSalesMan(sid);
        return s.getPerformanceRecordByYear(record.getYear()) != null;
    }

    @Override
    public void createSalesMan(SalesMan salesman) {
        if(salesManExists(salesman.getId())) {
            throw new ManagePersonalException("SalesMan-ID already exists");
        }
        salesmenCollection.insertOne(salesman.toDocument());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        if(!salesManExists(salesMan.getId())) {
            throw new ManagePersonalException("SalesMan does not exist");
        }
        if(recordExists(salesMan.getId(), record)){
            throw new ManagePersonalException("Performance Record for SalesMan already exists for year " + record.getYear());
        }
        Document update = new Document("$push", new Document("performanceRecords", record.toDocument()));
        salesmenCollection.updateOne(eq("sid", salesMan.getId()), update);

        //
        // import static com.mongodb.client.model.Updates.* // importing set, pull etc
        // salesman.updateOne( eq(SalesMan.Constants.SID, salesMan.getId()), pushEach( SocialPerformanceRecord.Constants.KEY_OF_RECORD, Array.asList()))
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        if(!salesManExists(sid)){
            throw new ManagePersonalException("SalesMan does not exist");
        }

        Document d = salesmenCollection.find(eq("sid", sid)).first();
        if (d == null) return null;
        return documentToSalesMan(d);
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> allSalesMen = new ArrayList<>();

        for (Document doc : salesmenCollection.find()) {
            allSalesMen.add(documentToSalesMan(doc));
        }

        return allSalesMen;

    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        if(!salesManExists(salesMan.getId())){
            throw new ManagePersonalException("SalesMan does not exist");
        }

        return readSalesMan(salesMan.getId()).getPerformanceRecords();
    }

    @Override
    public void deleteSalesMan(int sid) {

        if(!salesManExists(sid)){
            throw new ManagePersonalException("SalesMan does not exist");
        }
        salesmenCollection.deleteOne(eq("sid", sid));
    }

    @Override
    public void deleteRecord(int sid, int year) {
        if (!salesManExists(sid)) {
            throw new ManagePersonalException("SalesMan does not exist");
        }
        Document pull = new Document("performanceRecords", eq("year", year));
        Document update = new Document("$pull", pull);

        salesmenCollection.updateOne(eq("sid", sid), update);
    }

    @Override
    public void deleteAllRecordsBySalesMan(int sid) {
        if (!salesManExists(sid)) {
            throw new ManagePersonalException("SalesMan does not exist");
        }

        Document update = new Document("$set", new Document("performanceRecords", new ArrayList<>()));
        salesmenCollection.updateOne(eq("sid", sid), update);
    }

    @Override
    public void close() {
        this.client.close();
    }

}
