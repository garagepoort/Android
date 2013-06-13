package com.cegeka.alarmmanager.view;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import be.cegeka.android.alarms.transferobjects.AlarmTO;

import com.cegeka.alarmmanager.R;
import com.cegeka.alarmmanager.db.LocalAlarmRepository;
import com.cegeka.alarmmanager.infrastructure.InternetChecker;
import com.cegeka.alarmmanager.sync.AlarmSyncer;
import com.cegeka.alarmmanager.utilities.UserLoginLogOut;

public class UpdateActivity extends Activity implements Observer
{
	private ListView listView;
	private Button updateButton;
	private Button logOutButton;
	private TextView textViewOffline;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		AlarmSyncer.getInstance().addObserver(this);
		initWidgets();
	}

	private void initWidgets()
	{
		listView = (ListView) findViewById(R.id.listViewAlarms);
		updateButton = (Button) findViewById(R.id.updateButton);
		logOutButton = (Button) findViewById(R.id.logOutButton);
		textViewOffline = (TextView) findViewById(R.id.textViewOffline);

		initListview();

		if (!InternetChecker.isNetworkAvailable(this)
				|| !UserLoginLogOut.userLoggedIn(this))
		{
			updateButton.setVisibility(View.INVISIBLE);
			logOutButton.setVisibility(View.INVISIBLE);
			textViewOffline.setText("You are viewing the alarms offline.");
			textViewOffline.setTextSize(17);
		}
	}

	/**
	 * Initialize the list of alarms.
	 * 
	 * @param alarms
	 *            The alarms the list should show.
	 */
	private void initListview()
	{
		ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(this,
				android.R.layout.simple_list_item_1,
				LocalAlarmRepository.getLocalAlarms(this));
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AlarmListItemClickListener());
	}

	public void updateAlarms(View view)
	{
		if (InternetChecker.isNetworkAvailable(this))
		{
			progressDialog = ProgressDialog.show(UpdateActivity.this,
					"Please wait", "Loading please wait..", true);
			progressDialog.setCancelable(true);
			AlarmSyncer.getInstance().syncAllAlarms(this);

		} else
		{
			redirectToMainActivity();
			finish();
		}
	}

	public void logOut(View view)
	{
		UserLoginLogOut.logOutUser(this);
		redirectToMainActivity();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}

	private void redirectToMainActivity()
	{
		Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void update(Observable observable, Object data)
	{
		initListview();

		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}

	private class AlarmListItemClickListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3)
		{
			final AlarmTO alarm = (AlarmTO) listView.getItemAtPosition(position);
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					final AlertDialog alertDialog = new AlertDialog.Builder(
							UpdateActivity.this).create();
					alertDialog.setTitle(alarm.getTitle());
					alertDialog.setMessage(alarm.toString());
					alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									alertDialog.cancel();
								}
							});
					alertDialog.show();
				}
			});
		}
	}
}
