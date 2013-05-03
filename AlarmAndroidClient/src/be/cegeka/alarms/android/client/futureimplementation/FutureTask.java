package be.cegeka.alarms.android.client.futureimplementation;

import android.os.AsyncTask;

@SuppressWarnings("rawtypes")
public abstract class FutureTask extends AsyncTask<String, Future, Object>
{
	private Future future;
	
	public FutureTask(){
		future = new Future();
	}
	
	public Future executeFuture(String ... uri){
		super.execute(uri);
		return future;
	}
	
	protected Future getFuture()
	{
		return future;
	}
}
