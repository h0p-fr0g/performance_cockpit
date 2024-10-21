package org.hbrs.se.ws24;

import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBControllerImpl implements MongoDBControllerInterface {

    private static final String MONGODB_COLLECTION = "demostory";
    private static final String MONGODB_HOST = "iar-mongo.inf.h-brs.de";
    private static final int MONGODB_PORT = 27017;
    private static final String MONGODB_DATABASE = "demo";
    private static final String MONGODB_USERNAME = "demo";
    private static final String MONGODB_PASSWORD = "demo!";
    private static final String MONGODB_AUTH_DATABASE = MONGODB_DATABASE;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

        @Override
        public void openConnection() throws IllegalStateException{
            if(this.mongoClient != null){
                throw new IllegalStateException("MongoDbController is already open");
            }

            String authString = "";
            if(!(MONGODB_USERNAME.trim().isEmpty())){
                authString = MONGODB_USERNAME + ":" + MONGODB_PASSWORD + "@";
            }

            String connectionString = "mongodb://" + authString + MONGODB_HOST + ":" + MONGODB_PORT + "/" + "?authSource=" + MONGODB_AUTH_DATABASE;
            this.mongoClient = MongoClients.create(connectionString);

            this.mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE);
            this.mongoCollection = this.mongoDatabase.getCollection(MONGODB_COLLECTION);
        }

        @Override
        public void closeConnection() {
            if(this.mongoClient == null) return;
            this.mongoClient.close();
            this.mongoDatabase = null;
            this.mongoCollection = null;
        }

        @Override
        public void insertUserStory(UserStory story) {
            this.mongoCollection.insertOne(this.storyToDocument(story));
        }

        @Override
        public void updateUserStory(int id, UserStory story) {
            this.mongoCollection.replaceOne(Filters.eq("id", id), storyToDocument(story));
        }

        @Override
        public void deleteUserStory(int id) {
            this.mongoCollection.deleteOne(Filters.eq("id", id));
        }

        @Override
        public void clearUserStories(){
            this.mongoCollection.drop();
        }

        @Override
        public UserStory readUserStory(int id) {
            Document doc = this.mongoCollection.find(Filters.eq("id", id)).first();
            if (doc == null) return null;
            return documentToStory(doc);
        }

        @Override
        public ArrayList<UserStory> listUserStories() {
            ArrayList<UserStory> results = new ArrayList<UserStory>();
            this.mongoCollection.find().map(this::documentToStory).into(results);
            return results;
        }

        private Document storyToDocument(UserStory story){
            Document doc = new Document();
            doc.append("id", story.getId());
            doc.append("titel", story.getTitel());
            doc.append("project", story.getProject());
            doc.append("prio", story.getPrio());
            doc.append("aufwand", story.getAufwand());
            doc.append("mehrwert", story.getMehrwert());
            doc.append("risk", story.getRisk());
            doc.append("strafe", story.getStrafe());

            return doc;
        }

        private UserStory documentToStory(Document doc){
            UserStory story = new UserStory(doc.getInteger("id"), doc.getString("titel"), doc.getInteger("mehrwert"), doc.getInteger("strafe"), doc.getInteger("aufwand"), doc.getInteger("risk"), doc.getDouble("prio"));
            story.setProject(doc.getString("project"));
            return story;
        }


}
