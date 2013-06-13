package com.cegeka.alarmmanager.exceptions;

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
