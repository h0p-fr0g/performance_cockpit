import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.hbrs.ia.code.ManagePersonal;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import static com.mongodb.client.model.Filters.eq;

public class ManagePersonalImplementation implements ManagePersonal {

    private final MongoClient client;
    private final MongoCollection<Document> salesmenCollection;
    private static final String DATABASE_NAME = "Database";
    private static final String SALESMEN_COLLECTION_NAME = "salesmen";

    public ManagePersonalImplementation() {

        this.client = MongoClients.create(System.getenv("MONGO_URL"));
        MongoDatabase database = client.getDatabase(DATABASE_NAME);
        this.salesmenCollection = database.getCollection(SALESMEN_COLLECTION_NAME);


    }

    private SalesMan documentToSalesMan(Document doc) {

        return new SalesMan(
                doc.getString("firstname"),
                doc.getString("lastname"),
                doc.getInteger("sid")
        );
    }

    public void createSalesMan(SalesMan record) {

        salesmenCollection.insertOne(record.toDocument());
    }

    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

        SalesMan s = readSalesMan(salesMan.getId());
        s.addPerformanceRecord(record);
        salesmenCollection.replaceOne(eq("sid", s.getId()), s.toDocument());
    }

    public SalesMan readSalesMan(int sid) {

        Document d = salesmenCollection.find(Filters.eq("sid", sid)).first();
        if (d == null) return null;
        return documentToSalesMan(d);
    }

    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> allSalesMen = new ArrayList<>();

        try {
            for (Document doc : salesmenCollection.find()) {
                allSalesMen.add(documentToSalesMan(doc));
            }
        } catch (Exception e) {
            System.err.println("Datenbankfehler beim Lesen aller SalesMen: " + e.getMessage());
        }
        return allSalesMen;

    }

    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {

        return readSalesMan(salesMan.getId()).getPerformanceRecords();
    }
}
