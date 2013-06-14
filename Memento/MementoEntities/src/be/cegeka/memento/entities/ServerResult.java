package be.cegeka.memento.entities;

import java.io.Serializable;

public class ServerResult implements Serializable{
	
	private static final long serialVersionUID = -6199167455198207928L;
	private String exceptionMessage;
	private String data;
	
	public void setExceptionMessage(String e){
		this.exceptionMessage = e;
	}

	public String getExceptionMessage(){
		return exceptionMessage;
	}
	
	public boolean succesful(){
		if(getExceptionMessage() == null) {
			return true;
		}
		return false;
	}
	
	public String getData(){
		return data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	public enum ErrorMessage {
		
		SUCCES,
		FAIL;	
	}
}
