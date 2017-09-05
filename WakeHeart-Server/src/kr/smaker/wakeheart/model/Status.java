package kr.smaker.wakeheart.model;

import org.bson.Document;
import org.servoframework.database.DBConnector;
import org.servoframework.database.implement.Model;
import kr.smaker.wakeheart.config.Config;


public class Status implements Model {
    private static DBConnector connector = new DBConnector(Config.DB_NAME);;
    private static String collection = Config.collection[1];

    public void connect() {
		connector.connect();
    }
    
    @Override
    public void init() {
    	Document document = new Document();
    	
    	document.put("index", "");
    	document.put("status", "");
    	document.put("user", "");
    	document.put("time", "");
    	document.put("gender", "");
    	document.put("age", "");

        connector.insertQuery(collection, document);
    }

    public void insertAll(Float status, int user, String time, int gender, int age) {
    	Document document = new Document();

    	document.put("status", status);
    	document.put("user", user);
    	document.put("time", time);
    	document.put("gender", gender);
    	document.put("age", age);
    	
    	connector.insertQuery(collection, document);
    }
    
    @Override
    public void insert(String name) {
        connector.insertQuery(collection, new Document("name", name));
    }

    @Override
    public String find(String name) {
        return connector.findQuery(collection, new Document("name", name));
    }

    @Override
    public void update(String fieldName, Object value, Object newValue) {
        connector.updateQuery(collection, fieldName, value, newValue);
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public int count() {
        return connector.countQuery(collection);
    }

    @Override
    public void close() {
        connector.close();
    }
}
