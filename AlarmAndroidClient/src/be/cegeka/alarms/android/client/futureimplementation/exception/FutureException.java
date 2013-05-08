package be.cegeka.alarms.android.client.futureimplementation.exception;

public class FutureException extends Exception{
	public FutureException(){
		super();
	}
	
	public FutureException(String msg){
		super(msg);
	}
	
	public FutureException(Exception e){
		super(e);
	}
}
