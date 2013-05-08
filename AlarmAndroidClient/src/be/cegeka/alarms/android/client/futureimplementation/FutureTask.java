package be.cegeka.alarms.android.client.futureimplementation;

import android.os.AsyncTask;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.futureimplementation.exception.FutureException;

@SuppressWarnings("rawtypes")
public abstract class FutureTask<T, S> extends AsyncTask<S , Future, T> {
	private Future future;
	private Exception exception;

	public FutureTask() {
		future = new Future();
	}

	public Future executeFuture(S... uri) {
		super.execute(uri);
		return future;
	}

	protected Future getFuture() {
		return future;
	}

	@Override
	protected T doInBackground(S... uri) {
		try {
			return doInBackgroundFuture(uri);
		} catch (Exception e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(T result) {
		if (exception == null) {
			future.setValue(result);
			onPostExecuteFuture(result);
		}else{
			getFuture().setError(new TechnicalException(exception.getMessage()));
		}
	}

	protected abstract void onPostExecuteFuture(T result);

	protected abstract T doInBackgroundFuture(S ... uri) throws FutureException;

}
