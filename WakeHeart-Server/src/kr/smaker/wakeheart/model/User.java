package kr.smaker.wakeheart.model;

import org.bson.Document;
import org.servoframework.database.DBConnector;
import org.servoframework.database.implement.Model;
import kr.smaker.wakeheart.config.Config;

public class User implements Model {
    private static DBConnector connector = new DBConnector("wakeheart");;
    private static String collection = Config.collection[0];

    
    public void connect() {
		connector.connect();
    }
    
	@Override
	public void init() {		
		connector.insertQuery(collection, new Document("index", ""));
		connector.insertQuery(collection, new Document("id", ""));
		connector.insertQuery(collection, new Document("password", ""));
	}

	
	public String find(String fleid, String data) {
        return connector.findQuery(collection, new Document(fleid, data));
	}
	
	public String findAll() {
    	return connector.findAllQuery(collection).toString();
    }

	
	@Override
	public String find(String id) {
        return connector.findQuery(collection, new Document("id", id));
	}
	
	public Boolean auth(String id, String password) {
		Document document = new Document();

    	document.put("id", id);
    	document.put("password", password);
    	
    	String result = null;
    	
    	try {
            result = connector.findQuery(collection, new Document(document));
    	} catch (NullPointerException e) {
    		result = null;
    	}
        
        if (result != null) {
        	return true;
        } else {
            return false;
        }
    }
	
	@Override
	public void insert(String arg0) {
		
	}

	public void insertAll(String id, String password, int gender, int age) {
    	Document document = new Document();

    	document.put("id", id);
    	document.put("password", password);
    	document.put("gender", gender);
    	document.put("age", age);
    	
    	connector.insertQuery(collection, document);
    }
	
	@Override
    public void update(String fieldName, Object value, Object newValue) {
        connector.updateQuery(collection, fieldName, value, newValue);
    }
	
	@Override
	public int count() {
		return connector.countQuery(collection);
	}
	
	@Override
	public void delete(String arg0) {
		
	}
	
	@Override
	public void close() {
		
	}

}
