package com.redhat.gpse;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.WriteConcern;

public class BasicTest {
	
	public static void main(String[] args) {
		Mongo mConnection = null;
		try {
			mConnection = new Mongo("localhost", 27017);
			System.out.println("main() mConnection = "+mConnection);
		}catch(Exception x){
			throw new RuntimeException(x);
		}
		
		WriteConcern wConcern = new WriteConcern(1, 2000);
		mConnection.setWriteConcern(wConcern);
		
		DB mDatabase = mConnection.getDB("crawler");
		DBCollection coll = mDatabase.getCollection("sites");
		
		DBObject doc = new BasicDBObject();
		String[] tags = {"database", "open-source"};
		doc.put("url",  "org.mongodb");
		doc.put("tags", tags);
		
		DBObject attrs = new BasicDBObject();
		attrs.put("lastAccess", new Date());
		attrs.put("pingtime", 20);
		doc.put("attrs", attrs);
		
		coll.insert(doc);
		
		System.out.println("Initial document:n");
		System.out.println(doc.toString());
		
		coll.update(new BasicDBObject("_id", doc.get("_id")), new BasicDBObject("$set", new BasicDBObject("pingtime", 30)));
		DBCursor cursor = coll.find();
		
		System.out.println("after updaten");
		System.out.println(cursor.next().toString());
		
		System.out.println("# of site docs: "+coll.count());
		coll.remove(new BasicDBObject());
	}

}
