package be.cegeka.memento.futuretasks;

import be.cegeka.android.flibture.FutureTask;


public class SendContactsTask extends FutureTask<Integer, Object>
{
	@Override
	protected Integer doInBackgroundFuture(Object... arg0) throws Exception
	{
		Thread.sleep(875);
		// throw new Exception("lalala");
		return 15;
	}
}
