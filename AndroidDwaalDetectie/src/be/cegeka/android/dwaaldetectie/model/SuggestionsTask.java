package be.cegeka.android.dwaaldetectie.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import be.cegeka.android.dwaaldetectie.utilities.AddressConverter;
import be.cegeka.android.dwaaldetectie.utilities.NetworkCheck;
import be.cegeka.android.dwaaldetectie.view.MapView;


/**
 * Gets address suggestions from a GeoCoder for a partial address String.
 */
public class SuggestionsTask extends AsyncTask<MapView, Void, MapView>
{
	Geocoder geocoder;
	List<String> addresses;
	private String locationName;


	/**
	 * 
	 * @param locationName
	 *            Partial address String, suggestions are based on this String.
	 */
	public SuggestionsTask(String locationName)
	{
		super();
		this.locationName = locationName;
	}


	/**
	 * Code within this method is performed in the UI Thread before the actual
	 * task is performed in a separate Thread. All interaction with UI
	 * components has to be performed in the UI Thread.
	 */
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}


	/**
	 * This method is run in a separate Thread because getting a result from the
	 * GeoCoder could take a long time. The GeoCoder will find up to 20 results
	 * for the supplied String. "Belgium" is added to the String to make sure
	 * most results are within Belgium.
	 */
	@Override
	protected MapView doInBackground(MapView... params)
	{
		MapView mapView = params[0];

		if (new NetworkCheck(mapView).isOnline())
		{
			geocoder = new Geocoder(mapView);
			addresses = new ArrayList<String>();
			List<Address> addressesResult = null;

			try
			{
				addressesResult = geocoder.getFromLocationName(locationName + ", belgium", 20);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			if (addressesResult != null && !addressesResult.isEmpty())
			{
				for (Address address : addressesResult)
				{
					addresses.add(AddressConverter.convertAddress(address));
				}
			}
		}

		return mapView;
	}


	/**
	 * Code within this method is performed in the UI Thread after the actual
	 * task is performed in a separate Thread. All interaction with UI
	 * components has to be performed in the UI Thread. In this method the
	 * AddressSuggestionListAdapter is updated with the result.
	 */
	@Override
	protected void onPostExecute(MapView result)
	{
		super.onPostExecute(result);

		result.getAdapter().clear();

		if (addresses != null)
		{
			for (String adres : addresses)
			{
				result.getAdapter().add(adres);
			}
		}

		result.getAdapter().notifyDataSetChanged();
	}

}
