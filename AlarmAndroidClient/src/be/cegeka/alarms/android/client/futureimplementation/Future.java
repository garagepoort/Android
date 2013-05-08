package be.cegeka.alarms.android.client.futureimplementation;

import java.util.ArrayList;

public class Future<T> {

	private T value;
	private ArrayList<FutureCallable> futureCallables = new ArrayList<FutureCallable>();

	public void setValue(T value) {
		this.value = value;
		notifyFutures(null);
	}

	public T getValue() {
		return value;
	}

	private void notifyFutures(Exception e) {
		if (e == null) {
			for (FutureCallable f : futureCallables) {
				f.onSucces(getValue());
			}
		} else {
			for (FutureCallable f : futureCallables) {
				f.onError(e);
			}
		}
	}

	public void registerFutureCallable(FutureCallable callable) {
		futureCallables.add(callable);
	}

	public void setError(Exception e) {
		notifyFutures(e);
	}
}
