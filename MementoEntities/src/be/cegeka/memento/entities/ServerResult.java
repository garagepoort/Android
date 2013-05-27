package be.cegeka.memento.entities;

import java.io.Serializable;

public class ServerResult<T> implements Serializable{
	
	private static final long serialVersionUID = -6199167455198207928L;
	private Exception exception;
	private T data;
	
	public void setException(Exception e){
		this.exception = e;
	}

	public Exception getException(){
		return exception;
	}
	
	public boolean succesful(){
		if(getException() == null) {
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
