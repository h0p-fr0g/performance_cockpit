package org.hbrs.mongodb.test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.hbrs.ia.model.SalesMan;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.cdimascio.dotenv.Dotenv;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HighPerformanceTest {

    private  static MongoClient client;
    private MongoDatabase supermongo;
    private MongoCollection<Document> salesmen;

    // Load the .env file
    static Dotenv dotenv = Dotenv.load();
    static String db_uri = dotenv.get("DB_URI");
    static String db_test = dotenv.get("DB_TEST");

    @BeforeAll
    static void open() {
        client = MongoClients.create(db_uri);
    }

    @AfterAll
    static void close() {
        if (client != null) client.close();
    }

    @BeforeEach
    void setUp() {
        supermongo = client.getDatabase(db_test);
        salesmen = supermongo.getCollection("salesmen");
        salesmen.drop(); //making sure to start clean
    }

    @Test
    void insertSalesMan() {
        // CREATE (Storing) the salesman object
        Document document = new Document();
        document.append("firstname" , "Sascha");
        document.append("lastname" , "Alda");
        document.append("sid" , 90133);

        // ... now storing the object
        salesmen.insertOne(document);

        // READ (Finding) the stored Documnent
        Document newDocument = this.salesmen.find().first();
        System.out.println("Printing the object (JSON): " + newDocument );

        // Assertion
        Integer sid = (Integer) newDocument.get("sid");
        assertEquals( 90133 , sid );

        salesmen.drop();
    }

    @Test
    void insertSalesManMoreObjectOriented() {
        // CREATE (Storing) the salesman business object
        // Using setter instead
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90444 );

        // ... now storing the object
        salesmen.insertOne(salesMan.toDocument());

        // READ (Finding) the stored Documnent
        // Mapping Document to business object would be fine...
        Document newDocument = this.salesmen.find().first();
        System.out.println("Printing the object (JSON): " + newDocument );

        // Assertion
        Integer sid = (Integer) newDocument.get("sid");
        assertEquals( 90444 , sid );

        salesmen.drop();
    }
}