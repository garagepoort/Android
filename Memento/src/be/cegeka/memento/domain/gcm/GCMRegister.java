package be.cegeka.memento.domain.gcm;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;


public class GCMRegister
{

	public void registerWithGCMServer(final Context context)
	{
		System.out.println("Trying to register");
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals(""))
		{
			GCMRegistrar.register(context, "362183860979");
			System.out.println("new registration started");
		}
		else
		{
			Log.v("", "Already registered");
			System.out.println("already registered");
		}
	}


	public void unregister(Context context)
	{
		GCMRegistrar.unregister(context);
		System.out.println("Trying to UN-register");
	}
}
