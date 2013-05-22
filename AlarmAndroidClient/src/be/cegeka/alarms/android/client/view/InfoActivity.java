package be.cegeka.alarms.android.client.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.domain.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.domain.models.LoginModel;
import be.cegeka.alarms.android.client.presenter.MainPresenter;
import be.cegeka.alarms.android.client.shouldertap.events.LoginEvent;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class InfoActivity extends Activity {
	private Button showAlarmsButton;
	private Button loginButton;
	private TextView loginText;
	private View spinner;
	private View infoActivityContent;
	private InternetChecker internetChecker;
	private MainPresenter mainPresenter;
	private LogoutShoulder logoutShoulder;
	@Override
	protected void onStart() {
		super.onStart();
		logoutShoulder = new LogoutShoulder();
		mainPresenter = new MainPresenter(this);
		internetChecker = new InternetChecker();
		LoginModel.getInstance().addShoulder(logoutShoulder);

		inittializeViews();
		setUpContentView();
	}

	@Override
	protected void onDestroy() {
		LoginModel.getInstance().removeShoulder(logoutShoulder);
		super.onDestroy();
	}

	private void inittializeViews() {
		setTitle("Info");
		showAlarmsButton = (Button) findViewById(R.id.ToonAlarmenButton);
		loginButton = (Button) findViewById(R.id.LogInButton);
		loginText = (TextView) findViewById(R.id.LogInTextView);
		spinner = findViewById(R.id.logout_status);
		infoActivityContent = findViewById(R.id.linearLayout1);
	}

	private void setUpContentView() {
		UserTO userTO = LoginModel.getInstance().getLoggedInUser();

		if (userTO != null) {
			loginText.setText(getString(R.string.logged_in_information) + "\n" + userTO.getNaam() + " " + userTO.getAchternaam());
			loginButton.setText(getString(R.string.button_log_out));
			loginButton.setOnClickListener(new LogOutListener());
			showAlarmsButton.setVisibility(View.VISIBLE);
		}

		else {
			loginText.setText(getString(R.string.not_logged_in));
			loginButton.setText(getString(R.string.button_log_in));
			loginButton.setOnClickListener(new LogInListener());
			showAlarmsButton.setVisibility(View.GONE);
		}
	}

	public void showAlarms(View view) {
		Intent intent = new Intent(InfoActivity.this, SavedAlarmsActivity.class);
		startActivity(intent);
	}


	
	private class LogoutShoulder extends Shoulder<LoginEvent>{

		public LogoutShoulder() {
			super(LoginEvent.class);
		}

		@Override
		public void update(LoginEvent event) {
			if (event.getData() == null) {
				stopLogoutAnimation();
			}
		}
		
	}

	private void startLogoutAnimation() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			startLogoutWithFadingSpinner();
		} else {
			startLogoutWithoutFadingSpinner();
		}
	}

	private void stopLogoutAnimation() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			stopLogoutWithFadingSpinner();
		} else {
			stopLogoutWithoutFadingSpinner();
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private void startLogoutWithFadingSpinner() {
		spinner.setVisibility(View.VISIBLE);
		spinner.setAlpha(0);

		spinner.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				spinner.setVisibility(View.VISIBLE);
			}
		});

		infoActivityContent.animate().setDuration(200).alpha(0).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				infoActivityContent.setVisibility(View.GONE);

				mainPresenter.logout();
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public void stopLogoutWithFadingSpinner() {
		setUpContentView();

		spinner.animate().setDuration(200).alpha(0).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				spinner.setVisibility(View.GONE);
			}
		});

		infoActivityContent.animate().setDuration(200).alpha(1).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				infoActivityContent.setVisibility(View.VISIBLE);
			}
		});

	}

	private void startLogoutWithoutFadingSpinner() {
		mainPresenter.logout();
	}

	private void stopLogoutWithoutFadingSpinner() {
		setUpContentView();
	}

	private class LogInListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (internetChecker.isNetworkAvailable(InfoActivity.this)) {
				Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		}
	}

	private class LogOutListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			startLogoutAnimation();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
}
