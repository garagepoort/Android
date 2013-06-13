package be.cegeka.android.dwaaldetectie.utilities;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;


public class LatLngLocationConverter
{
	/**
	 * Converts a LatLng object into a Location object. The distance between two
	 * point can only be calculated on the Location object, therefore the LatLng
	 * object needs to be converted sometimes.
	 * 
	 * @param latLng
	 *            LatLng object that needs to be converted.
	 * @return Location.
	 */
	public static Location locationFromLatLng(LatLng latLng)
	{
		Location location = new Location("location");
		location.setLatitude(latLng.latitude);
		location.setLongitude(latLng.longitude);

		return location;
	}
}
