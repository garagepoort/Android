package be.cegeka.android.dwaaldetectie.view.listeners;

import static be.cegeka.android.dwaaldetectie.model.TrackingConfiguration.trackingConfig;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;
import be.cegeka.android.dwaaldetectie.R;
import be.cegeka.android.dwaaldetectie.utilities.LatLngLocationConverter;


/**
 * The onLocationChanged method is called when the Location has changed and the
 * listener is registered to receive LocationUpdates from the LocationManager.
 * It will calculate the current distance from the home location and update the
 * TrackingConfiguration variables.
 */
public class LocationChangeListener extends LocationChangeAdapter
{
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private Toast toast;


	/**
	 * Constructor initialises Toast which is shown when the distance becomes
	 * too big.
	 * 
	 * @param context
	 *            The Context of the Application.
	 */
	@SuppressLint("ShowToast")
	public LocationChangeListener(Context context)
	{
		toast = Toast.makeText(context, R.string.toast_too_far, Toast.LENGTH_LONG);
	}


	@Override
	public void onLocationChanged(Location location)
	{
		if (trackingConfig().getLocation() != null)
		{
			if (isBetterLocation(location, LatLngLocationConverter.locationFromLatLng(trackingConfig().getLocation())))
			{
				trackingConfig().updateDistance(location);
			}

			if (location.distanceTo(LatLngLocationConverter.locationFromLatLng(trackingConfig().getLocation())) > trackingConfig().getMaxDistance())
			{
				 toast.show();
			}
			else
			{
				 toast.cancel();
			}
		}
	}


	// COPIED:
	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location, Location currentBestLocation)
	{
		if (currentBestLocation == null)
		{
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer)
		{
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		}
		else if (isSignificantlyOlder)
		{
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate)
		{
			return true;
		}
		else if (isNewer && !isLessAccurate)
		{
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
		{
			return true;
		}
		return false;
	}


	/**
	 * Checks whether two providers are the same
	 * */
	private boolean isSameProvider(String provider1, String provider2)
	{
		if (provider1 == null)
		{
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
