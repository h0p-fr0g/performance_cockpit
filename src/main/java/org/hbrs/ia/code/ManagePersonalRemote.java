package org.hbrs.ia.code;

public class ManagePersonalRemote extends ManagePersonalAbstract {
    // note: bevor running the application
    // set the environment variable MONGO_URL to the connectionstring of your database

    public ManagePersonalRemote() {
        super(System.getenv("MONGO_URL"));
    }
}
