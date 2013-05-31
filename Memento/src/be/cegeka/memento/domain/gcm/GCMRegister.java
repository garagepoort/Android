package be.cegeka.memento.domain.gcm;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class GCMRegister {

	public void registerWithGCMServer(final Context context) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
//			Toast.makeText(context, "registering with gcm", Toast.LENGTH_LONG).show();
			GCMRegistrar.register(context, "362183860979");

		} else {
//			Toast.makeText(context, "already registered", Toast.LENGTH_LONG).show();
			Log.v("", "Already registered");
		}
	}

	public void unregister(Context context) {
		GCMRegistrar.unregister(context);
	}
}
