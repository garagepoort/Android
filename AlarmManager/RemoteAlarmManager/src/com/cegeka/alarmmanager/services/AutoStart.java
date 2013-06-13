package com.cegeka.alarmmanager.services;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver{

	/**
	 * Auto start the alarmservice on start-up
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		ServiceStarterStopper.startSyncService(context);
	}

}
