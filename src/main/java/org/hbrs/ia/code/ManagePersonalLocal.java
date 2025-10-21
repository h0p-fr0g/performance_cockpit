package org.hbrs.ia.code;

public class ManagePersonalLocal extends ManagePersonalAbstract {
    // note: bevor running the application
    // make sure you are running a mongod instance in the same directory as the application
    // >> mongod --dbpath data/db

    private static final Integer PORT = 27017;

    public ManagePersonalLocal() {
        super("mongodb://localhost:"+ PORT +"/");
    }

}
