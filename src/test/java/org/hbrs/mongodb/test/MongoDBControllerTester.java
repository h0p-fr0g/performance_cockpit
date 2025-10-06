package org.hbrs.mongodb.test;

import org.hbrs.mongodb.MongoDBControllerImpl;
import org.hbrs.mongodb.MongoDBControllerInterface;
import org.hbrs.mongodb.UserStory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoDBControllerTester {

    private MongoDBControllerInterface mongoDBController;

    @BeforeEach
    void setUp() {
        mongoDBController = new MongoDBControllerImpl();
        mongoDBController.openConnection();
    }

    @AfterEach
    void tearDown() {
        mongoDBController.closeConnection();
    }

    @Test
    public void insertAndReadUserStory() {
        UserStory userStory = new UserStory();
        userStory.setProject("MongoDB");
        userStory.setAufwand(4);
        userStory.setId(2223424);

        mongoDBController.insertUserStory( userStory );
        UserStory newStory = mongoDBController.readUserStory( 2223424 );

        System.out.println(newStory);
        assertEquals( userStory , newStory  );
    }

}
