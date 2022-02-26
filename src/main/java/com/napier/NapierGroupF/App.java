package com.napier.NapierGroupF;

//Imports
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App
{
    public static void main(String[] args)
    {
        //Connecting to MongoDb on Local System - On port 27000
        MongoClient mongoClient = new MongoClient("mongo-dbserver");
        //Get a db called mydb
        MongoDatabase database = mongoClient.getDatabase("mydb");
        //Get a Collection from the DB
        MongoCollection<Document> collection = database.getCollection("test");
        //Create a Document to store
        // testing if I edit this, does my push counts
        Document doc = new Document("group", "Group F")
                .append("members", "Gail, Katrina, Josh, Hamza")
                .append("class", "Software Engineering Methods")
                .append("year", "2022")
                .append("result", new Document("CW", 100).append("EX", 100));
        //Add document to Collection
        collection.insertOne(doc);

        //Check document in collection
        Document myDoc = collection.find().first();
        //Print Doc contents as JSON
        System.out.println(myDoc.toJson());
    }
}
