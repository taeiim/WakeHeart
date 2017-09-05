package kr.smaker.wakeheart.model;

import org.bson.Document;
import org.servoframework.database.DBConnector;
import org.servoframework.database.implement.Model;
import kr.smaker.wakeheart.config.Config;


public class Device implements Model {
    private static DBConnector connector = new DBConnector(Config.DB_NAME);;
    private static String collection = Config.collection[2];

    public void connect() {
		connector.connect();
    }
    
    @Override
    public void init() {
        connector.connect();
        connector.insertQuery(collection, new Document("code", ""));
        connector.insertQuery(collection, new Document("user", ""));
        connector.insertQuery(collection, new Document("imei", ""));
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
