package com.cegeka.alarmmanager.exceptions;

public class TechnicalException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public TechnicalException(){
		super();
	}
	
	public TechnicalException(String msg){
		super(msg);
	}
	
	public TechnicalException(Exception e){
		super(e);
	}

}
