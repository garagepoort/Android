package be.cegeka.alarms.android.client.gcm;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class GCMRegister {

	public void registerWithGCMServer(Context context) {
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
