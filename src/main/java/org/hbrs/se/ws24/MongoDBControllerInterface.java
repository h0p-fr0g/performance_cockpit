package org.hbrs.se.ws24;

import java.util.ArrayList;

 public interface MongoDBControllerInterface {
        void openConnection();
        void closeConnection();

        void insertUserStory(UserStory story);
        void updateUserStory(int id, UserStory story);
        void deleteUserStory(int id);
        void clearUserStories();
        UserStory readUserStory(int id);
        ArrayList<UserStory> listUserStories();
 }