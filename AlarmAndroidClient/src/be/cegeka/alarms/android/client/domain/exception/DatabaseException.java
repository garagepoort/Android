package be.cegeka.alarms.android.client.domain.exception;

@SuppressWarnings("serial")
public class DatabaseException extends Exception{
	
	public DatabaseException(){
		super();
	}
	
	public DatabaseException(String msg){
		super(msg);
	}
	
	public DatabaseException(Exception e){
		super(e);
	}

}
