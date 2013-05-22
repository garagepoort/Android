package be.cegeka.alarms.android.client.shouldertap.events;

import be.cegeka.android.ShouldrTap.AbstractEvent;

public class LoginErrorEvent extends AbstractEvent<Exception> {

	Exception exception;
	
	@Override
	public Exception getData() {
		return exception;
	}

	@Override
	public void setData(Exception exception) {
		this.exception = exception;
	}
}
