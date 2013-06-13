package com.cegeka.alarmmanager;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.android.alarms.transferobjects.UserTO;

import com.cegeka.alarmmanager.sync.AlarmSyncer;
import com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection.RemoteDBConnectionInterface;
import com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection.RemoteDBWebConnection;
import com.cegeka.alarmmanager.utilities.UserLoginLogOut;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
		super("362183860979");
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		System.out.println("ERROR: " + arg1);
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		Toast.makeText(arg0, "message received!!!!!!", Toast.LENGTH_SHORT);
		AlarmSyncer.getInstance().syncAllAlarms(arg0);
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		UserTO user = UserLoginLogOut.getLoggedInUser(arg0);
		RemoteDBConnectionInterface remoteDBConnection = new RemoteDBWebConnection();
		remoteDBConnection.registerSenderID(user.getEmail(), arg1);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		RemoteDBConnectionInterface remoteDBConnection = new RemoteDBWebConnection();
		remoteDBConnection.unRegisterSenderID(arg1);
		
	}

}
