package be.cegeka.alarms.android.client.exception;

public class UtilityException extends Exception {

	private static final long serialVersionUID = 1102712012965235791L;

	public UtilityException(){
		super();
	}
	
	public UtilityException(String msg){
		super(msg);
	}
	
	public UtilityException(Exception e){
		super(e);
	}
	
}
