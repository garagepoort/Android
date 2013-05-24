package be.cegeka.alarms.android.client.domain.gcm;

import static be.cegeka.android.flibture.Future.whenResolved;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.domain.alarmSync.AlarmSyncer;
import be.cegeka.alarms.android.client.domain.controllers.ServerCalls;
import be.cegeka.alarms.android.client.domain.login.LoginController;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService
{

	public GCMIntentService()
	{
		super("362183860979");
	}


	@Override
	protected void onError(Context arg0, String arg1)
	{
		System.out.println("ERROR: " + arg1);
	}


	@Override
	protected void onMessage(final Context context, Intent arg1)
	{
		new AlarmSyncer().syncAllAlarms(context);
		Toast.makeText(context, "MESSAGE RECEIVED", Toast.LENGTH_LONG).show();
	}


	@Override
	protected void onRegistered(final Context arg0, String gcmid)
	{
		System.out.println("REGISTERED WITH GCM!!!!");
		UserTO user = new LoginController(arg0).getLoggedInUser();
		Future<Boolean> future = new ServerCalls(arg0).registerUser(user.getEmail(), gcmid);
		whenResolved(future, new FutureCallable<Boolean>()
		{

			@Override
			public void onSucces(Boolean result)
			{
				Toast.makeText(arg0, "REGISTERD SENDERID", Toast.LENGTH_SHORT).show();
				System.out.println("REGISTERED WITH WEBSERVICE!!!");
			}


			@Override
			public void onError(Exception e)
			{
				e.printStackTrace();
			}
		});
	}


	@Override
	protected void onUnregistered(final Context context, String registrationID)
	{
		Future<Boolean> future = new ServerCalls(context).unregisterUser(registrationID);
		whenResolved(future, new FutureCallable<Boolean>() {

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onSucces(Boolean success) {
				Toast.makeText(context, "Succesfully unregistered", Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
