package be.cegeka.memento.domain.shoulders;

import android.app.Activity;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.domain.events.ErrorEvent;
import be.cegeka.memento.view.DialogCreator;


public class ErrorShoulder extends Shoulder<ErrorEvent>
{
	private Activity activity;


	public ErrorShoulder(Activity activity)
	{
		super(ErrorEvent.class);
		this.activity = activity;
	}


	@Override
	public void update(ErrorEvent event)
	{
		event.getData().printStackTrace();
		DialogCreator.showErrorDialog(event.getData().getMessage(), activity);
	}

}
