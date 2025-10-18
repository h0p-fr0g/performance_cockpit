package org.hbrs.ia.code;

import com.mongodb.MongoClient;

public class ManagePersonalLocal extends ManagePersonalImplementation {

    public ManagePersonalLocal() {
        super(new MongoClient("localhost", 27017));
    }

}
