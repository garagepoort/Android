package be.cegeka.android.dwaaldetectie.view;

import static be.cegeka.android.dwaaldetectie.model.TrackingConfiguration.trackingConfig;
import java.util.Observable;
import java.util.Observer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import be.cegeka.android.dwaaldetectie.R;
import be.cegeka.android.dwaaldetectie.dialogs.HandleProblemStartFactory;
import be.cegeka.android.dwaaldetectie.dialogs.HandleProblemStartInterface;
import be.cegeka.android.dwaaldetectie.model.AddressLoaderSaver;
import be.cegeka.android.dwaaldetectie.model.TrackingConfiguration;
import be.cegeka.android.dwaaldetectie.model.TrackingService;
import be.cegeka.android.dwaaldetectie.utilities.NetworkCheck;
import be.cegeka.android.dwaaldetectie.view.listeners.DialogDismissListener;


/**
 * Activity which is opened when the app is launched, this is the startscreen.
 * It holds several buttons and displays home address and distance from home
 * address when Service is enabled.
 */
public class MainActivity extends Activity implements Observer
{
	private NetworkCheck networkCheck;
	private ToggleButton startButton;
	private TextView textView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	@Override
	protected void onStart()
	{
		super.onStart();

		trackingConfig().loadVariables(this);
		trackingConfig().addObserver(this);
		
		initFields();
		initListeners();
	}


	/**
	 * Initialises instance variables.
	 */
	private void initFields()
	{
		networkCheck = new NetworkCheck(this);
		textView = (TextView) findViewById(R.id.textView2);
		startButton = (ToggleButton) findViewById(R.id.startButton1);
		System.out.println(TrackingService.isRunning());
		if (TrackingService.isRunning())
		{
			startButton.setChecked(true);
			updateDisplay();
		}
	}


	/**
	 * Adds all Listeners.
	 */
	private void initListeners()
	{
		startButton.setOnClickListener(new StartButtonListener());
	}


	/**
	 * Is called when the users clicks the "configure address" button, changes
	 * Activity to the MapView.
	 * 
	 * @param view
	 */
	public void handleShowMap(View view)
	{
		if (!networkCheck.isOnline())
		{
			Toast.makeText(this, R.string.map_info_no_internet, Toast.LENGTH_SHORT).show();
		}
		else
		{
			Intent intent = new Intent(this, MapView.class);
			startActivity(intent);
		}
	}


	/**
	 * Is called when the user clicks the "set max distance" button, shows a
	 * dialog in which the maximum distance can be set and saved.
	 * 
	 * @param view
	 */
	public void handleMaxDistance(View view)
	{
		EditText editText = new EditText(this);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setPadding(50, 20, 50, 20);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
				.setTitle(R.string.main_dialogMaxDistance_title)
				.setMessage(R.string.main_dialogMaxDistance_message)
				.setView(editText)
				.setNegativeButton(getString(R.string.button_cancel), new DialogDismissListener())
				.setPositiveButton(getString(R.string.button_save), new SaveMaxDistanceListener(editText));

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		alertDialog.show();
	}


	/**
	 * Updates the textView that shows the home address and distance from the
	 * home address.
	 */
	private void updateDisplay()
	{
		if (trackingConfig().getAddress() != null)
		{
			textView.setText(trackingConfig().getAddress() + "\n\n" + trackingConfig().getDistance());
		}
		else
		{
			textView.setText(trackingConfig().getDistance());
		}
		
		startButton.setChecked(true);
	}


	/**
	 * Handles the problems that can occur when the user tries to start the
	 * service. Based on the problem code an appropriate error dialog is shown.
	 */
	private void handleProblemStart(int problem)
	{
		//Handling the dialog is passed on.
		HandleProblemStartInterface handleProblemStart = new HandleProblemStartFactory().getHandleProblemStart(problem);
		handleProblemStart.handle(this);
		
//		OLD CODE ---
//		if (problem == TrackingConfiguration.RESULT_NO_ADDRESS_SET)
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(this)
//					.setTitle(getString(R.string.error_error))
//					.setMessage(getString(R.string.error_no_address_saved))
//					.setPositiveButton(getString(R.string.error_button_positive), new ConfigureAddressClickListener())
//					.setNegativeButton(getString(R.string.button_cancel), new DialogDismissListener());
//
//			builder.create().show();
//		}
//		else if (problem == TrackingConfiguration.RESULT_NO_MAX_DISTANCE)
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(this)
//					.setTitle(getString(R.string.error_error))
//					.setMessage(getString(R.string.error_no_max_distance_saved))
//					.setPositiveButton(getString(R.string.error_button_positive), new ConfigureMaxDistanceClickListener())
//					.setNegativeButton(getString(R.string.button_cancel), new DialogDismissListener());
//
//			builder.create().show();
//		}
//		else
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(this)
//					.setTitle(getString(R.string.error_error))
//					.setMessage(getString(R.string.error_unable_to_start))
//					.setNegativeButton(getString(R.string.button_cancel), new DialogDismissListener());
//
//			builder.create().show();
//		}
	}


	@Override
	public void update(Observable observable, Object data)
	{
		updateDisplay();
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * OnClickListener for the start button. Starts the Service if the button
	 * gets enabled and a home address and maximum distance to the home address
	 * is set, stops the Service if the button gets disabled. Uses the
	 * problemStart method if the result of the start is not RESULT_OK
	 */
	private class StartButtonListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (!startButton.isChecked())
			{
				stopService(new Intent(MainActivity.this, TrackingService.class));
				textView.setText("");
			}
			else
			{
				int result = trackingConfig().startTrackingService(MainActivity.this);

				if (result != TrackingConfiguration.RESULT_OK)
				{
					startButton.setChecked(false);
					handleProblemStart(result);
				}
				else
				{
					updateDisplay();
				}
			}
		}
	}


	/**
	 * OnClickListener for the max distance dialog. Parses the input to a long
	 * and sets and saves this input. The EditText in which the max distance is
	 * typed needs to be passed in the Constructor.
	 */
	private class SaveMaxDistanceListener implements AlertDialog.OnClickListener
	{
		private final EditText editText;


		private SaveMaxDistanceListener(EditText editText)
		{
			this.editText = editText;
		}


		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			String text = editText.getText().toString();
			try
			{
				long distance = Long.parseLong(text);
				trackingConfig().setMaxDistance(distance);
				AddressLoaderSaver.saveMaxDistance(MainActivity.this, distance);
			}
			catch (NumberFormatException e)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
						.setTitle(getString(R.string.error_error))
						.setMessage(getString(R.string.error_no_valid_number))
						.setPositiveButton(getString(R.string.error_button_positive), new ConfigureMaxDistanceClickListener())
						.setNegativeButton(getString(R.string.button_cancel), new DialogDismissListener());

				builder.create().show();
			}
		}
	}


	/**
	 * OnClickListener for the error dialog if the maximum distance is not set
	 * when the user tries to start the TrackingService.
	 */
	public class ConfigureMaxDistanceClickListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			handleMaxDistance(null);
		}
	}


	/**
	 * OnClickListener for the error dialog if the address is not set when the
	 * user tries to start the TrackingService.
	 */
	public class ConfigureAddressClickListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			handleShowMap(null);
		}
	}
}
