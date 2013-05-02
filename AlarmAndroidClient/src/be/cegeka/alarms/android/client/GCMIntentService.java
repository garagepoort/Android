package be.cegeka.alarms.android.client;

import java.util.ArrayList;

import synchronisation.RemoteAlarmController;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;

import com.google.android.gcm.GCMBaseIntentService;

import futureimplementation.Future;
import futureimplementation.FutureCallable;
import futureimplementation.FutureService;

public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
		super("362183860979");
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		System.out.println("ERROR: " + arg1);
	}

	@Override
	protected void onMessage(final Context context, Intent arg1) {
		Future<ArrayList<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(new LoginController(this).getLoggedInUser());
		FutureService.whenResolved(future, new FutureCallable<ArrayList<AlarmTO>>() {

			@Override
			public void apply(ArrayList<AlarmTO> result) {
				new LocalAlarmRepository(context).replaceAll(result);
				Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	@Override
	protected void onRegistered(final Context arg0, String arg1) {
		System.out.println("REGISTERED WITH GCM!!!!");
		UserTO user = new LoginController(arg0).getLoggedInUser();
		Future<Boolean> future = new RemoteAlarmController().registerUser(user.getEmail(), arg1);
		FutureService.whenResolved(future, new FutureCallable<Boolean>() {

			@Override
			public void apply(Boolean result) {
				Toast.makeText(arg0, "REGISTERD SENDERID", Toast.LENGTH_SHORT).show();
				System.out.println("REGISTERED WITH WEBSERVICE!!!");
			}
		});
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
//		RemoteDBConnectionInterface remoteDBConnection = new RemoteDBWebConnection();
//		remoteDBConnection.unRegisterSenderID(arg1);
		
	}
}
