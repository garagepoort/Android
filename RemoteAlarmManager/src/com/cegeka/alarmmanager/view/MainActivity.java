package com.cegeka.alarmmanager.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.cegeka.alarmmanager.R;
import com.cegeka.alarmmanager.infrastructure.InternetChecker;
import com.cegeka.alarmmanager.utilities.UserLoginLogOut;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initializeStart();
	}

	private void initializeStart()
	{
		if (InternetChecker.isNetworkAvailable(this))
		{
			if (!UserLoginLogOut.userLoggedIn(this))
			{
				redirectToLoginActivity();
			} else
			{
				redirectToUpdateActivity();
			}
			finish();
		} else
		{
			setContentView(R.layout.activity_main);
		}
	}

	public void retry(View view)
	{
		initializeStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	private void redirectToUpdateActivity()
	{
		Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
		startActivity(intent);
	}

	public void redirectToUpdate(View view)
	{
		redirectToUpdateActivity();
	}

	private void redirectToLoginActivity()
	{
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
	}
}
