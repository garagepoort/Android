package com.cegeka.cascadingcallstest.exceptions;

public class DatabaseException extends Exception {
	
	public DatabaseException(){
		super();
	}
	
	public DatabaseException(String message){
		super(message);
	}
	
	public DatabaseException(Exception ex){
		super(ex);
	}
}
