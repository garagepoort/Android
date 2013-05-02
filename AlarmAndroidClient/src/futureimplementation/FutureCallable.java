package futureimplementation;

import be.cegeka.alarms.android.client.exception.TechnicalException;


public interface  FutureCallable<T> {

	public void apply(T result, ResultCode resultCode);

	
}
