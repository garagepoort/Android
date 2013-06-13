package be.cegeka.android.dwaaldetectie.model;

import static be.cegeka.android.dwaaldetectie.model.TrackingConfiguration.trackingConfig;
import be.cegeka.android.dwaaldetectie.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Starts the app as soon as the device is booted up. The class itself will only
 * make sure the TrackingService is started when a Broadcast is received, in the
 * AndroidManifest this class needs to be added as a BroadcastReceiver and in
 * the AndroidManifest it is determined on which occasion a Broadcast is sent to
 * this class (e.g. BOOT_COMPLETED).
 */
public class AutoStart extends BroadcastReceiver
{
	public void onReceive(Context ctx, Intent arg1)
	{
		int result = trackingConfig().startTrackingService(ctx);

		if (result == TrackingConfiguration.RESULT_ERROR || result == TrackingConfiguration.RESULT_NO_ADDRESS_SET)
		{
			Toast.makeText(ctx, R.string.error_unable_to_start, Toast.LENGTH_LONG).show();
		}
	}
}