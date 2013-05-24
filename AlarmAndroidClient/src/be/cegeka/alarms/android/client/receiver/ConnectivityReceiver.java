package be.cegeka.alarms.android.client.receiver;

import static be.cegeka.android.flibture.Future.whenResolved;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.domain.controllers.LocalAlarmRepository;
import be.cegeka.alarms.android.client.domain.controllers.ServerCalls;
import be.cegeka.alarms.android.client.domain.gcm.GCMRegister;
import be.cegeka.alarms.android.client.domain.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.domain.login.LoginController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;


public class ConnectivityReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(final Context context, Intent intent)
	{
		if(new InternetChecker().isNetworkAvailable(context) && new LoginController(context).isUserLoggedIn()){
			new GCMRegister().registerWithGCMServer(context);
			
			Future<List<AlarmTO>> future = new ServerCalls(context).getAllAlarms(new LoginController(context).getLoggedInUser());
			whenResolved(future, new FutureCallable<List<AlarmTO>>() {

				@Override
				public void onSucces(List<AlarmTO> result)
				{
					new LocalAlarmRepository(context).replaceAll(result);
					Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onError(Exception e)
				{
					e.printStackTrace();
				}
				
			});
		}
	}

}
