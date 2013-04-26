package be.cegeka.alarms.android.client.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.infrastructure.LoginController;

public class InfoActivity extends Activity {

	private Button forceSyncButton;
	private Button loginButton;
	private TextView loginText;
	private InternetChecker internetChecker;
	private LoginController loginController;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}

	@Override
	protected void onStart() {
		super.onStart();
		internetChecker = new InternetChecker();
		loginController = new LoginController();
		setTitle("Info");
		inittializeViews();
		if (loginController.isUserLoggedIn(this)) {
			loginText.setText(getString(R.string.logged_in_information));
			loginButton.setText(getString(R.string.button_log_out));
			forceSyncButton.setVisibility(View.VISIBLE);
		} else {
			loginText.setText(getString(R.string.not_logged_in));
			loginButton.setText(getString(R.string.button_log_in));
			forceSyncButton.setVisibility(View.GONE);
		}
	}

	private void inittializeViews() {
		forceSyncButton = (Button) findViewById(R.id.ForceSyncButton);
		loginButton = (Button) findViewById(R.id.LogInButton);
		loginText = (TextView) findViewById(R.id.LogInTextView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	// BUTTONS
	public void logIn(View view) {
		if (!loginController.isUserLoggedIn(this)) {
			if (internetChecker.isNetworkAvailable(this)) {
				Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
				startActivity(intent);
			} else {
				buildAndShowErrorDialog();
			}
		} else {
			loginController.logOutUser(this);

		}
	}

	public void showAlarms(View view) {
		Intent intent = new Intent(InfoActivity.this, SavedAlarmsActivity.class);
		startActivity(intent);
	}

	public void buildAndShowErrorDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
				builder.setMessage(getString(R.string.error_message_no_internet)).setPositiveButton(getString(R.string.button_error_message_accept), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
				builder.create().show();
			}
		});
	}

	/**
	 * ONLY FOR TESTING.
	 * 
	 * @param internetChecker
	 */
	public void setInternetChecker(InternetChecker internetChecker) {
		this.internetChecker = internetChecker;
	}
	
	/**
	 * ONLY FOR TESTING.
	 * 
	 * @param controller
	 */
	public void setLoginController(LoginController controller){
		this.loginController=controller;
	}

}