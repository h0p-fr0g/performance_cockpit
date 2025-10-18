package org.hbrs.ia.code;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import static com.mongodb.client.model.Filters.eq;


abstract class ManagePersonalImplementation implements ManagePersonal {

    protected final GenericMongoClient client;
    protected final MongoCollection<Document> salesmenCollection;
    protected static final String DATABASE_NAME = "Database";
    protected static final String SALESMEN_COLLECTION_NAME = "salesmen";

    public ManagePersonalImplementation(GenericMongoClient client) {
        this.client = client;
        this.salesmenCollection = this.client.getCollection(DATABASE_NAME, SALESMEN_COLLECTION_NAME);
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

        if(!salesManExists(salesman.getId())) {
            salesmenCollection.insertOne(salesman.toDocument());
        }
        else{
            throw new ManagePersonalException("SalesMan-ID already exists");
        }
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

        if(salesManExists(salesMan.getId())) {

            if(!recordExists(salesMan.getId(), record)){

                Document update = new Document("$push", new Document("performanceRecords", record.toDocument()));
                salesmenCollection.updateOne(eq("sid", salesMan.getId()), update);
            }
            else{
                throw new ManagePersonalException("Performance Record for SalesMan already exists for year " + record.getYear());
            }
        }
        else{
            throw new ManagePersonalException("SalesMan does not exist");
        }
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

        if(salesManExists(sid)){

            salesmenCollection.deleteOne(eq("sid", sid));
        }
        else{
            throw new ManagePersonalException("SalesMan does not exist");
        }
    }

    @Override
    public void deleteRecord(int sid, int year) {
        if (salesManExists(sid)) {

            Document pull = new Document("performanceRecords", eq("year", year));
            Document update = new Document("$pull", pull);

            salesmenCollection.updateOne(eq("sid", sid), update);
        } else {
            throw new ManagePersonalException("SalesMan does not exist");
        }
    }

    @Override
    public void deleteAllRecordsBySalesMan(int sid) {

        if (salesManExists(sid)) {

            Document update = new Document("$set", new Document("performanceRecords", new ArrayList<>()));
            salesmenCollection.updateOne(eq("sid", sid), update);
        } else {
            throw new ManagePersonalException("SalesMan does not exist");
        }
    }

    @Override
    public void close() {
        this.client.close();
    }

}
