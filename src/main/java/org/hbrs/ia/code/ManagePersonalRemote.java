package org.hbrs.ia.code;

import com.mongodb.client.MongoClients;

public class ManagePersonalRemote extends ManagePersonalImplementation {

    public ManagePersonalRemote() {
        super(MongoClients.create(System.getenv("MONGO_URL")));
    }
}
