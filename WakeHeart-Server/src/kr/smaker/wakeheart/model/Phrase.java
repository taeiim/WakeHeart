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
        connector.insertQuery(collection, new Document("description", "같은 실수를 두려워 하되, 새로운 실수를 두려워하지 말라"));
        connector.insertQuery(collection, new Document("description", "교육의 위대한 목표는 앎이 아니라 행동이다"));
        connector.insertQuery(collection, new Document("description", "강력한 이유는 강력한 행동을 낳는다."));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자4"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자5"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자6"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자7"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자8"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자9"));
        connector.insertQuery(collection, new Document("description", "공부 열심히 하자10"));
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
