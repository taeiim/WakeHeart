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
		connector.insertQuery(collection, new Document("nickname", ""));
	}

	
	public String find(String fleid, String data) {
        return connector.findQuery(collection, new Document(fleid, data));
	}
	
	@Override
	public String find(String data) {
        return connector.findQuery(collection, new Document("name", data));
	}
	
	@Override
	public void insert(String arg0) {
		
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
