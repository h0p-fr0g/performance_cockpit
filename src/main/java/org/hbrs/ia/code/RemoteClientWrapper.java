package org.hbrs.ia.code;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class RemoteClientWrapper implements GenericMongoClient {
    private final com.mongodb.client.MongoClient client;

    public RemoteClientWrapper(com.mongodb.client.MongoClient client) {
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
