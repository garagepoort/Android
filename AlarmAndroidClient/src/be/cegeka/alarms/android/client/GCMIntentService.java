package be.cegeka.alarms.android.client;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.FutureService;
import be.cegeka.alarms.android.client.futureimplementation.ResultCode;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localAlarmSync.LocalToAndroidAlarmSyncer;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
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
		new LocalToAndroidAlarmSyncer(context).unscheduleAllAlarms();

		Future<List<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(new LoginController(this).getLoggedInUser());
		FutureService.whenResolved(future, new FutureCallable<ArrayList<AlarmTO>>()
		{

			@Override
			public void onSucces(ArrayList<AlarmTO> result)
			{
				new LocalAlarmRepository(context).replaceAll(result);
				new LocalToAndroidAlarmSyncer(context).scheduleAllAlarms();
				Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
			}


			@Override
			public void onError(Exception e)
			{
				// TODO Auto-generated method stub

			}
		});
	}


	@Override
	protected void onRegistered(final Context arg0, String gcmid)
	{
		System.out.println("REGISTERED WITH GCM!!!!");
		UserTO user = new LoginController(arg0).getLoggedInUser();
		Future<Boolean> future = new RemoteAlarmController().registerUser(user.getEmail(), gcmid);
		FutureService.whenResolved(future, new FutureCallable<Boolean>()
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
				// TODO Auto-generated method stub

			}
		});
	}


	@Override
	protected void onUnregistered(Context arg0, String arg1)
	{
		// RemoteDBConnectionInterface remoteDBConnection = new
		// RemoteDBWebConnection();
		// remoteDBConnection.unRegisterSenderID(arg1);

	}
}
