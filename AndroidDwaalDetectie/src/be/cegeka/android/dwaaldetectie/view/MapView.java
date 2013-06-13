package be.cegeka.android.dwaaldetectie.view;

import static be.cegeka.android.dwaaldetectie.model.TrackingConfiguration.trackingConfig;
import java.io.IOException;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import be.cegeka.android.dwaaldetectie.R;
import be.cegeka.android.dwaaldetectie.model.AddressSuggestionListAdapter;
import be.cegeka.android.dwaaldetectie.model.SuggestionsTask;
import be.cegeka.android.dwaaldetectie.utilities.AddressConverter;
import be.cegeka.android.dwaaldetectie.utilities.NetworkCheck;
import be.cegeka.android.dwaaldetectie.view.listeners.DialogDismissListener;
import be.cegeka.android.dwaaldetectie.view.listeners.TextWatcherAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Activity which holds the map on which the user can set a home address.
 */
public class MapView extends Activity
{
	private NetworkCheck networkCheck;
	private LatLng latLng;
	private GoogleMap map;
	private String tempAddressDescription;
	private String addressDescription;
	private AddressSuggestionListAdapter adapter;
	private AutoCompleteTextView textView;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);

		initFields();
		initMap();
	}


	/**
	 * Initialises instance variables.
	 */
	private void initFields()
	{
		networkCheck = new NetworkCheck(this);

		adapter = new AddressSuggestionListAdapter(this, android.R.layout.simple_list_item_1);
		adapter.setNotifyOnChange(true);
	}


	/**
	 * Gets the reference to the GoogleMap object and adds ActionListeners to
	 * it.
	 */
	private void initMap()
	{
		if (map == null)
		{
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		}
		map.setMyLocationEnabled(true);
		map.setOnMapClickListener(new MyMapClickListener());
		map.setOnMapLongClickListener(new MyMapLongClickListener());
		map.setOnInfoWindowClickListener(new MyInfoWindowClickListener());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.map_view, menu);
		return true;
	}


	/**
	 * @return AddressSuggestionListAdapter
	 */
	public AddressSuggestionListAdapter getAdapter()
	{
		return adapter;
	}


	/**
	 * Creates and displays a dialog in which the user can search for an
	 * address. The user gets suggestions based on GoogleMaps suggestions.
	 */
	private void createAddressSearchDialog()
	{
		textView = new AutoCompleteTextView(MapView.this);
		textView.setThreshold(2);
		textView.setInputType(InputType.TYPE_CLASS_TEXT);
		textView.setAdapter(adapter);
		textView.setPadding(50, 20, 50, 20);
		textView.addTextChangedListener(new AddressTextWatcher());

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MapView.this)
				.setTitle(getString(R.string.map_dialog_search_title))
				.setView(textView)
				.setPositiveButton(getString(R.string.map_dialog_search_button_positive), new SearchForAddressClickListener())
				.setNegativeButton(getString(R.string.map_dialog_search_button_negative), new DialogDismissListener());

		AlertDialog alertDialog = dialogBuilder.create();
		alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		alertDialog.show();
	}


	/**
	 * Creates and displays a generic error dialog.
	 * 
	 * @param title
	 *            Title of the error dialog.
	 * @param message
	 *            Message of the error dialog.
	 * @param buttonText
	 *            Text of the button of the dialog.
	 */
	private void createErrorDialog(String title, String message, String buttonText)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapView.this);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setPositiveButton(buttonText, new DialogDismissListener());
		alertDialog.create().show();
	}


	/**
	 * Searches for the address typed by the user. If found, the location is
	 * displayed on the map and the camera zooms in on it. If not found, an
	 * error dialog is displayed.
	 */
	private void searchForAddress()
	{
		try
		{
			Geocoder geocoder = new Geocoder(MapView.this);
			List<Address> addresses = geocoder.getFromLocationName(textView.getText().toString(), 1);

			if (!addresses.isEmpty())
			{
				Address address = addresses.get(0);
				tempAddressDescription = AddressConverter.convertAddress(address);
				addressDescription = tempAddressDescription;
				latLng = new LatLng(address.getLatitude(), address.getLongitude());

				map.clear();
				showLatLngOnMap(latLng, tempAddressDescription);

				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
				map.animateCamera(cameraUpdate);
			}
			else
			{
				createErrorDialog(getString(R.string.map_dialog_not_found_title), getString(R.string.map_dialog_not_found_message), getString(R.string.map_dialog_not_found_button));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Looks up the address that corresponds to a location on the map. If not
	 * found, the latidue and longitude of the location will be used.
	 * 
	 * @param point
	 *            Location on the map.
	 */
	private void searchAddressForPoint(LatLng point)
	{
		latLng = point;

		try
		{
			Geocoder geocoder = new Geocoder(MapView.this);
			List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
			if (!addresses.isEmpty())
			{
				Address address = addresses.get(0);
				tempAddressDescription = AddressConverter.convertAddress(address);
				addressDescription = tempAddressDescription;
			}
			else
			{
				tempAddressDescription = getString(R.string.map_marker_message_no_address_found);
				addressDescription = latLng.latitude + ", " + latLng.longitude;
			}

			map.clear();
			showLatLngOnMap(point, tempAddressDescription);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Places a marker on the map at a certain point and with an InfoWindow.
	 * 
	 * @param point
	 *            Location where the marker will be placed.
	 * @param message
	 *            Text which will be shown in the InfoWindow.
	 */
	private void showLatLngOnMap(LatLng point, String message)
	{
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(point);
		markerOptions.title(message);
		markerOptions.draggable(false);
		Marker marker = map.addMarker(markerOptions);
		marker.showInfoWindow();
	}


	/**
	 * OnMapClickListener for the map. Displays an address search dialog when
	 * the user taps the map.
	 */
	private class MyMapClickListener implements OnMapClickListener
	{
		@Override
		public void onMapClick(LatLng arg0)
		{
			createAddressSearchDialog();
		}
	}


	/**
	 * OnMapLongClickListener for the map. Places a marker on the map where the
	 * user holds down on the map for a while.
	 */
	private class MyMapLongClickListener implements OnMapLongClickListener
	{
		@Override
		public void onMapLongClick(LatLng point)
		{
			if (!networkCheck.isOnline())
			{
				Toast.makeText(MapView.this, R.string.map_info_no_internet, Toast.LENGTH_SHORT).show();
			}
			else
			{
				searchAddressForPoint(point);
			}
		}
	}


	/**
	 * TextWatcher for the address search TextInput. Starts a new AsyncTask when
	 * the text is changed so new suggestions are displayed for the user.
	 */
	private class AddressTextWatcher extends TextWatcherAdapter
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			new SuggestionsTask(textView.getText().toString()).execute(MapView.this);
		}
	}


	/**
	 * OnClickListener for the search button in the address search dialog.
	 */
	private class SearchForAddressClickListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (!networkCheck.isOnline())
			{
				createErrorDialog(getString(R.string.map_info_no_internet_title), getString(R.string.map_info_no_internet), getString(R.string.map_dialog_not_found_button));
			}
			else
			{
				searchForAddress();
			}
		}
	}


	/**
	 * OnClickListener for the InfoWindow of a marker. Opens a dialog prompting
	 * if the user wants to use the selected address as home address.
	 */
	private class MyInfoWindowClickListener implements OnInfoWindowClickListener
	{
		@Override
		public void onInfoWindowClick(Marker marker)
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(MapView.this)
					.setTitle(getString(R.string.map_dialog_title))
					.setMessage(getString(R.string.map_dialog_message) + "\n" + addressDescription)
					.setNegativeButton(getString(R.string.map_dialog_button_negative), new DialogDismissListener())
					.setPositiveButton(getString(R.string.map_dialog_button_positive), new NewAddressClickListener());

			dialog.create().show();
		}
	}


	/**
	 * OnClickListener that sets and saves a new home address.
	 */
	private class NewAddressClickListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if (latLng != null)
			{
				try
				{
					trackingConfig().setAddress(addressDescription);
					trackingConfig().setLocation(MapView.this, latLng);

					Toast.makeText(MapView.this, getString(R.string.map_address_success), Toast.LENGTH_LONG).show();
					finish();
				}
				catch (IOException e)
				{
					Toast.makeText(MapView.this, getString(R.string.map_address_fail), Toast.LENGTH_LONG).show();
					finish();
					e.printStackTrace();
				}
			}
			else
			{
				Toast.makeText(MapView.this, getString(R.string.map_address_fail), Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
}
