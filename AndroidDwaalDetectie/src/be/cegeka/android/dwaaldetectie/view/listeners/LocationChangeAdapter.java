package be.cegeka.android.dwaaldetectie.view.listeners;

import android.location.LocationListener;
import android.os.Bundle;


/**
 * Adapter that implements the LocationListener interface and implements some of the methods with no content.
 * The onLocationChanged method stays abstract.
 */
public abstract class LocationChangeAdapter implements LocationListener
{
	@Override
	public void onProviderDisabled(String provider)
	{
		/**
		 * void
		 */
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		/**
		 * void
		 */
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		/**
		 * void
		 */
	}
}
