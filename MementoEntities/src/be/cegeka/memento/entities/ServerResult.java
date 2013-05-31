package be.cegeka.memento.entities;

import java.io.Serializable;

public class ServerResult<T> implements Serializable{
	
	private static final long serialVersionUID = -6199167455198207928L;
	private String exceptionMessage;
	private T data;
	
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
	
	public T getData(){
		return data;
	}
	
	public void setData(T data){
		this.data = data;
	}
	
	public enum ErrorMessage {
		
		SUCCES,
		FAIL;	
	}
}
