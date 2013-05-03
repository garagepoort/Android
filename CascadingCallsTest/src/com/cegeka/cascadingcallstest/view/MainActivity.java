package com.cegeka.cascadingcallstest.view;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cegeka.cascadingcallstest.db.ContactsLoaderSaver;
import com.example.cascadingcallstest.R;

public class MainActivity extends Activity implements Observer{

	private SIPController sipController;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sipController = new SIPController(this);
		sipController.addObserver(this);
		sipController.closeProfile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void login(View view){

		String username = ((EditText) findViewById(R.id.nameField)).getText().toString();
		String password = ((EditText) findViewById(R.id.Numberfield)).getText().toString();

		sipController.RegisterProfile(username, password);
//		sipController.closeProfile();

	}

	public void call(View view){
		sipController.callContacts(ContactsLoaderSaver.loadContacts());
		final Button callButton = (Button) findViewById(R.id.callButton);
		callButton.setText("Stop Calling");
		callButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sipController.stopCalling();
				callButton.setText("Call");
				callButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						call(v);
					}
				});
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		sipController.closeProfile();
		super.onDestroy();
	}

	public void startSetContactsActivity(View view){
		Intent intent = new Intent(this, SetContactsActivity.class);
		startActivity(intent);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		Boolean registered = (Boolean) arg1;
		
		if(registered){
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					EditText usernameField = (EditText) findViewById(R.id.nameField);
					EditText passwordField = (EditText) findViewById(R.id.Numberfield);
					TextView infoLabel = (TextView) findViewById(R.id.title);
					Button loginButton = (Button) findViewById(R.id.loginButton);
					Button callButton = (Button) findViewById(R.id.callButton);

					usernameField.setVisibility(View.INVISIBLE);
					passwordField.setVisibility(View.INVISIBLE);
					infoLabel.setVisibility(View.VISIBLE);
					callButton.setEnabled(true);

					infoLabel.setText("You're logged in as " + sipController.getProfileName());
					loginButton.setText("Log out");
					loginButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							sipController.closeProfile();
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						}
					});

				}
			});
		}
		else if(!registered){
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}



}
