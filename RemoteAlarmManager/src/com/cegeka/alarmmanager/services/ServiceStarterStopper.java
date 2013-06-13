package com.cegeka.alarmmanager.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class ServiceStarterStopper {

	public static void startSyncService(Context ctx) {
		Intent alarmIntent = new Intent(ctx, SyncService.class);
		ctx.startService(alarmIntent);
	}

	public static void stopSyncService(Context ctx) {
		Intent alarmIntent = new Intent(ctx, SyncService.class);
		ctx.stopService(alarmIntent);
	}

	public static void startGCMUpdates(Context context) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			GCMRegistrar.register(context, "362183860979");
		} else {
			Log.v("", "Already registered");
		}
	}
}
