package com.hiroshi.cimoc.core;

import android.content.res.Resources;

import com.hiroshi.cimoc.R;
import com.hiroshi.cimoc.model.Comic;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FEILONG on 2018/4/1.
 */

public class Mongo {
    private MongoClientURI mongoUrl;
    private MongoClient mongoClient;
    private MongoDatabase mongoBase;
    private MongoCollection<Document> comicBaseColl;
    private MongoCollection<Document> comicChaColl;

    private Document queryStr;
    private Document setStr;

    public Mongo(){
//        mongoUrl = new MongoClientURI("mongodb://comic:ba93Z5qUerhSJE3q@ds014118.mlab.com:14118/comic");
        mongoUrl = new MongoClientURI("mongodb://173.82.232.184:27017/comic");
        mongoClient = new MongoClient(mongoUrl);
        mongoBase = mongoClient.getDatabase("comic");
        comicBaseColl = mongoBase.getCollection("comic-base");
        comicChaColl = mongoBase.getCollection("comic-chapter");
    }

    public void UpdateComicBase(Comic comic,String lastcid){
        try{
            //search
            queryStr = new Document("lid",comic.getSource())
                            .append("mid",comic.getCid());
            Document d = comicBaseColl.find(queryStr).first();
            //if not exist,create it
            if(d == null){
                setStr = new Document("lid",comic.getSource())
                    .append("mid",comic.getCid())
                    .append("lastcid",lastcid)
                    .append("path",comic.getUrl())
                    .append("title",comic.getTitle())
                    .append("intro",comic.getIntro())
                    .append("author",comic.getAuthor());
                comicBaseColl.insertOne(setStr);
            }else
                //if update,refersh it
                if(!d.get("lastcid").equals(lastcid)) {
                    setStr = new Document("lastcid",lastcid);
                    comicBaseColl.updateOne(queryStr, new Document("$set",setStr));
                }
        }catch (Exception ex){
            //connect to databases error
        }
    }
}
