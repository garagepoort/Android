package be.cegeka.alarms.android.client.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.domain.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.domain.models.LoginModel;
import be.cegeka.alarms.android.client.presenter.MainPresenter;
import be.cegeka.alarms.android.client.shouldertap.events.LoginErrorEvent;
import be.cegeka.alarms.android.client.shouldertap.events.LoginEvent;
import be.cegeka.android.ShouldrTap.Shoulder;

public class LoginActivity extends Activity {
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	private String email;
	private String password;

	private EditText emailView;
	private EditText passwordView;
	private View loginFormView;
	private View loginStatusView;
	private TextView loginStatusMessageView;
	private LoginShoulder loginShoulder;
	private loginErrorShoulder loginErrorShoulder;

	private MainPresenter mainPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupActionBar();
		
		loginShoulder = new LoginShoulder();
		LoginModel.getInstance().addShoulder(loginShoulder);
		loginErrorShoulder = new loginErrorShoulder();
		LoginModel.getInstance().addShoulder(loginErrorShoulder);

		mainPresenter = new MainPresenter(this);
		email = getIntent().getStringExtra(EXTRA_EMAIL);
		emailView = (EditText) findViewById(R.id.email);
		emailView.setText(email);

		passwordView = (EditText) findViewById(R.id.password);
		passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				attemptLogin();
				return true;
			}
		});

		loginFormView = findViewById(R.id.login_form);
		loginStatusView = findViewById(R.id.login_status);
		loginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), InfoActivity.class));
			}

		});
	}

	@Override
	protected void onDestroy() {
		LoginModel.getInstance().removeShoulder(loginShoulder);
		LoginModel.getInstance().removeShoulder(loginErrorShoulder);
		super.onDestroy();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void attemptLogin() {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);

		emailView.setError(null);
		passwordView.setError(null);

		email = emailView.getText().toString();
		password = passwordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 4) {
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(email)) {
			emailView.setError(getString(R.string.error_field_required));
			focusView = emailView;
			cancel = true;
		} else if (!email.contains("@")) {
			emailView.setError(getString(R.string.error_invalid_email));
			focusView = emailView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			loginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			tryToLoginOnServer();

		}
	}

	private void tryToLoginOnServer() {
		if (new InternetChecker().isNetworkAvailable(this)) {
			mainPresenter.login(email, password);
		} else {
			DialogCreator.buildAndShowDialog(getString(R.string.error_title_general), getString(R.string.error_message_no_internet), this);
			showProgress(false);
		}
	}

	private class LoginShoulder extends Shoulder<LoginEvent> {

		public LoginShoulder() {
			super(LoginEvent.class);
		}

		@Override
		public void update(LoginEvent loginEvent) {
			showProgress(false);
			startActivity(new Intent(getApplicationContext(), InfoActivity.class));
		}
	}

	private class loginErrorShoulder extends Shoulder<LoginErrorEvent> {

		public loginErrorShoulder() {
			super(LoginErrorEvent.class);
		}

		@Override
		public void update(LoginErrorEvent event) {
			Exception exception = event.getData();
			DialogCreator.buildAndShowDialog(getString(R.string.error_title_general), exception.getMessage(), LoginActivity.this);
			LoginActivity.this.showProgress(false);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			loginStatusView.setVisibility(View.VISIBLE);
			loginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});

			loginFormView.setVisibility(View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});
		} else {
			loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}