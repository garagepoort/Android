package be.cegeka.memento.events;

import be.cegeka.android.ShouldrTap.AbstractEvent;


public class ErrorEvent extends AbstractEvent<Exception>
{
	private Exception exception;


	@Override
	public Exception getData()
	{
		return exception;
	}


	@Override
	public void setData(Exception exception)
	{
		this.exception = exception;
	}

}
