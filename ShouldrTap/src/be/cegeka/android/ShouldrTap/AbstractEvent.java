package be.cegeka.android.ShouldrTap;

public abstract class AbstractEvent<T> {

	private Exception exception;
	
	
	public abstract T getData();
	
	public abstract void setData(T data);

	// public Exception getException() {
	// return exception;
	// }
	//
	// public void setException(Exception exception) {
	// this.exception = exception;
	// }
	
	
}
