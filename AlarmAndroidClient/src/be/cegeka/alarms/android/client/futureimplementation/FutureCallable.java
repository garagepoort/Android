package be.cegeka.alarms.android.client.futureimplementation;

import be.cegeka.alarms.android.client.exception.TechnicalException;


public interface  FutureCallable<T> {

	public void onSucces(T result);

	public void onError(Exception e);
}
