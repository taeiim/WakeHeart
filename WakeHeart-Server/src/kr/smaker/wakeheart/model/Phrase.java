package kr.smaker.wakeheart.model;

import org.bson.Document;
import org.servoframework.database.DBConnector;
import org.servoframework.database.implement.Model;
import kr.smaker.wakeheart.config.Config;


public class Phrase implements Model {
    private static DBConnector connector = new DBConnector(Config.DB_NAME);;
    private static String collection = Config.collection[3];

    public void connect() {
		connector.connect();
    }
    
    @Override
	public void insert(String arg0) {
		// TODO Auto-generated method stub
		
	}
    
    @Override
    public void init() {
    	
    	
        connector.insertQuery(collection, new Document("name", ""));
        connector.insertQuery(collection, new Document("description", ""));
    }

    public void insertAll(String name, String description) {
    	Document doc = new Document();
    	doc.put("name", name);
    	doc.put("description", description);
    	
        connector.insertQuery(collection, doc);
    }
    
    public String findAll() {
    	return connector.findAllQuery(collection);
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
