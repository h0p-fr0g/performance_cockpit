import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.hbrs.ia.code.ManagePersonal;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class ManagePersonalImplementation implements ManagePersonal {

    private final MongoClient client;
    private final MongoCollection<Document> salesmenCollection;
    private static final String DATABASE_NAME = "Database";
    private static final String SALESMEN_COLLECTION_NAME = "salesmen";

    public ManagePersonalImplementation() {

        try {
            this.client = MongoClients.create(System.getenv("MONGO_URL"));
            MongoDatabase database = client.getDatabase(DATABASE_NAME);
            this.salesmenCollection = database.getCollection(SALESMEN_COLLECTION_NAME);
        }
        catch (Exception e) {
            throw new ManagePersonalException("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    private SocialPerformanceRecord documentToPerformanceRecord(Document doc) {

        return new SocialPerformanceRecord(
                doc.getInteger("id"),
                doc.getInteger("salesman"),
                doc.getInteger("year"),
                doc.getInteger("socialScore")
        );
    }

    private SalesMan documentToSalesMan(Document doc) {

        List<Document> performanceDocs = doc.getList("performanceRecords", Document.class);

        List<SocialPerformanceRecord> performanceRecords = performanceDocs.stream()
                .map(this::documentToPerformanceRecord) // Use the helper method for conversion
                .collect(Collectors.toList());

        return new SalesMan(
                doc.getString("firstname"),
                doc.getString("lastname"),
                doc.getInteger("sid"),
                performanceRecords
        );
    }

    private boolean salesManExists(SalesMan salesman){

        return salesmenCollection.find(eq("sid", salesman.getId())).first() != null;
    }

    private boolean recordExists(int sid, SocialPerformanceRecord record){

        SalesMan s = readSalesMan(sid);
        return s.getPerformanceRecordByYear(record.getYear()) != null;
    }

    @Override
    public void createSalesMan(SalesMan salesman) {

        if(!salesManExists(salesman)) {
            salesmenCollection.insertOne(salesman.toDocument());
        }
        else{
            throw new ManagePersonalException("SalesMan-ID already exists");
        }
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

        if(salesManExists(salesMan)) {

            if(!recordExists(salesMan.getId(), record)){

                SalesMan s = readSalesMan(salesMan.getId());
                s.addPerformanceRecord(record);
                salesmenCollection.replaceOne(eq("sid", s.getId()), s.toDocument());
            }
            else{
                throw new ManagePersonalException("Performance Record for SalesMan already exists for year " + record.getYear());
            }
        }
        else{
            throw new RuntimeException("SalesMan does not exist");
        }
    }

    @Override
    public SalesMan readSalesMan(int sid) {

        Document d = salesmenCollection.find(eq("sid", sid)).first();
        if (d == null) return null;
        return documentToSalesMan(d);
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> allSalesMen = new ArrayList<>();

        try {
            for (Document doc : salesmenCollection.find()) {
                allSalesMen.add(documentToSalesMan(doc));
            }
        } catch (Exception e) {
            System.err.println("Error reading all SalesMen: " + e.getMessage());
        }
        return allSalesMen;

    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {

        return readSalesMan(salesMan.getId()).getPerformanceRecords();
    }

    @Override
    public void deleteSalesMan(int sid) {

    }

    @Override
    public void deleteAllSalesMen() {

    }

    @Override
    public void deleteRecord(int sid, int year) {

    }

    @Override
    public void deleteAllRecords(int sid) {

    }
}
