package be.cegeka.alarms.android.client.services;

import static be.cegeka.alarms.android.client.futureimplementation.FutureService.whenResolved;
import java.util.ArrayList;
import java.util.List;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.widget.Toast;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.gcm.GCMRegister;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;


public class ConnectivityReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(final Context context, Intent intent)
	{
		Debug.waitForDebugger();
//		boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//		String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
//		boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);
//		NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//		NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
		
		if(new InternetChecker().isNetworkAvailable(context) && new LoginController(context).isUserLoggedIn()){
			new GCMRegister().registerWithGCMServer(context);
			
			Future<List<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(new LoginController(context).getLoggedInUser());
			whenResolved(future, new FutureCallable<ArrayList<AlarmTO>>() {

				@Override
				public void onSucces(ArrayList<AlarmTO> result)
				{
					new LocalAlarmRepository(context).replaceAll(result);
					Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onError(Exception e)
				{
				}
				
			});
		}
	}

}
