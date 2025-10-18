package org.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class LocalClientWrapper implements GenericMongoClient {
    private final MongoClient client;

    public LocalClientWrapper(MongoClient client) {
        this.client = client;
    }

    @Override
    public MongoCollection<Document> getCollection(String dbname, String collectionname) {
        return this.client.getDatabase(dbname).getCollection(collectionname);
    }

    @Override
    public void close() {
        this.client.close();
    }

}