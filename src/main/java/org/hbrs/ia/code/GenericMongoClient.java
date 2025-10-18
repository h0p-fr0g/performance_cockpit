package org.hbrs.ia.code;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface GenericMongoClient {
    MongoCollection<Document> getCollection(String dbname, String collectionname);
    void close();
}
