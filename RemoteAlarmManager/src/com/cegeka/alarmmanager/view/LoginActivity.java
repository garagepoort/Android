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
import android.widget.EditText;
import be.cegeka.android.alarms.transferobjects.UserTO;

import com.cegeka.alarmmanager.R;
import com.cegeka.alarmmanager.infrastructure.InternetChecker;
import com.cegeka.alarmmanager.services.ServiceStarterStopper;
import com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection.RemoteDBConnectionInterface;
import com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection.RemoteDBWebConnection;
import com.cegeka.alarmmanager.utilities.UserLoginLogOut;

public class LoginActivity extends Activity implements Observer {
	private EditText emailEditText;
	private EditText paswoordEditText;
	private RemoteDBConnectionInterface connector = new RemoteDBWebConnection();
	private String pass;
	private ProgressDialog myPd_ring;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initWidgets();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void initWidgets() {
		emailEditText = (EditText) findViewById(R.id.emailtextBox);
		paswoordEditText = (EditText) findViewById(R.id.paswoordTextBox);
	}

	public void login(View view) {
		if (InternetChecker.isNetworkAvailable(this)) {
			myPd_ring = ProgressDialog.show(LoginActivity.this, "Please wait",
					"Loading please wait..", true);
			myPd_ring.setCancelable(true);
			startLogin();
		} else {
			redirectToMainActivity();
			finish();
		}
	}

	private void startLogin() {
		String email = emailEditText.getText().toString();
		String pass = paswoordEditText.getText().toString();
		this.pass = pass;
		connector.addObserver(this);
		connector.startUserLogin(email, pass);
		
	}

	public void onClickRedirectToUpdate(View view) {
		redirectToUpdateActivity();
	}

	private void redirectToUpdateActivity() {
		Intent intent = new Intent(LoginActivity.this, UpdateActivity.class);
		startActivity(intent);
	}

	private void redirectToMainActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void update(Observable observable, Object data) {
		UserTO u = connector.getUser();
		myPd_ring.dismiss();

		if (u != null) {
			u.setPaswoord(pass);
			UserLoginLogOut.logInUser(LoginActivity.this, u);
//			ServiceStarterStopper.startSyncService(this);
			ServiceStarterStopper.startGCMUpdates(this);
			redirectToUpdateActivity();
			finish();
		} else {
			showAlartDialog();
		}
	}

	private void showAlartDialog() {
		final AlertDialog alertDialog = new AlertDialog.Builder(
				LoginActivity.this).create();
		alertDialog.setTitle("Login failed.");
		alertDialog
				.setMessage("Your username or password is incorrect. Try again.");
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						alertDialog.cancel();
					}
				});
		alertDialog.show();
	}

}
