package kr.smaker.wakeheart.model;

import org.servoframework.database.DBConnector;
import org.servoframework.database.implement.Model;

public class User implements Model {
    private static DBConnector connector = new DBConnector("wakeheart");;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String find(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insert(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
