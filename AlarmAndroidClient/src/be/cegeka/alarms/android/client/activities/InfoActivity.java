package be.cegeka.alarms.android.client.activities;

import static be.cegeka.alarms.android.client.futureimplementation.FutureService.whenResolved;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.FutureService;
import be.cegeka.alarms.android.client.futureimplementation.ResultCode;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localAlarmSync.AlarmToAndroidSchedulerSyncer;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;



public class InfoActivity extends Activity
{
	private Button forceSyncButton;
	private Button loginButton;
	private TextView loginText;
	private InternetChecker internetChecker;
	private LoginController loginController;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}


	@Override
	protected void onStart()
	{
		super.onStart();
		internetChecker = new InternetChecker();
		loginController = new LoginController(this);
		setTitle("Info");
		inittializeViews();
		if (loginController.isUserLoggedIn())
		{
			UserTO userTO = loginController.getLoggedInUser();
			loginText.setText(getString(R.string.logged_in_information) + "\n" + userTO.getNaam() + " " + userTO.getAchternaam());
			loginButton.setText(getString(R.string.button_log_out));
			loginButton.setOnClickListener(new LogOutListener());
			forceSyncButton.setVisibility(View.VISIBLE);
		}
		else
		{
			loginText.setText(getString(R.string.not_logged_in));
			loginButton.setText(getString(R.string.button_log_in));
			loginButton.setOnClickListener(new LogInListener());
			forceSyncButton.setVisibility(View.GONE);
		}
	}


	private void inittializeViews()
	{
		forceSyncButton = (Button) findViewById(R.id.ForceSyncButton);
		loginButton = (Button) findViewById(R.id.LogInButton);
		loginText = (TextView) findViewById(R.id.LogInTextView);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}


	private class LogInListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (internetChecker.isNetworkAvailable(InfoActivity.this))
			{
				Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			else
			{
				DialogCreator.buildAndShowDialog(getString(R.string.error_title_general), getString(R.string.error_message_no_internet), InfoActivity.this);
			}
		}
	}


	private class LogOutListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			loginController.logOutUser();
			InfoActivity.this.recreate();
		}
	}


	public void showAlarms(View view)
	{
		if(loginController.isUserLoggedIn())
		{
		Intent intent = new Intent(InfoActivity.this, SavedAlarmsActivity.class);
		startActivity(intent);
		}
		else
		{
			DialogCreator.buildAndShowDialog(getString(R.string.error_title_general), getString(R.string.error_message_not_logged_in), this);
		}
	}
	
	public void syncAlarms(View view){
		Future<List<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(new LoginController(this).getLoggedInUser());
		whenResolved(future, new FutureCallable<ArrayList<AlarmTO>>() {

			@Override
			public void onSucces(ArrayList<AlarmTO> result, ResultCode resultCode)
			{
				new LocalAlarmRepository(InfoActivity.this).replaceAll(result);
				Toast.makeText(InfoActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(Exception e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	/**
	 * ONLY FOR TESTING.
	 * 
	 * @param internetChecker
	 */
	public void setInternetChecker(InternetChecker internetChecker)
	{
		this.internetChecker = internetChecker;
	}


	/**
	 * ONLY FOR TESTING.
	 * 
	 * @param controller
	 */
	public void setLoginController(LoginController controller)
	{
		this.loginController = controller;
	}

}
