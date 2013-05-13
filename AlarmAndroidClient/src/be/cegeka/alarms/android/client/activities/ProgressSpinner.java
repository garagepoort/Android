package be.cegeka.alarms.android.client.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import be.cegeka.alarms.android.client.R;

public class ProgressSpinner extends Activity{

	private View spinner;
	private View originalView;
	
	public ProgressSpinner(Context context, View view){
		spinner = findViewById(R.id.logout_status);
		this.originalView=view;
	}
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show)
	{
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			int shortAnimTime = 200; // getResources().getInteger(android.R.integer.config_shortAnimTime);
			
			spinner.setVisibility(View.VISIBLE);
			spinner.animate().setDuration(shortAnimTime).alpha(show
					? 1
					: 0).setListener(new AnimatorListenerAdapter()
			{
				@Override
				public void onAnimationEnd(Animator animation)
				{
					spinner.setVisibility(show
							? View.VISIBLE
							: View.GONE);
				}
			});

			originalView.setVisibility(View.VISIBLE);
			originalView.animate().setDuration(shortAnimTime).alpha(show
					? 0
					: 1).setListener(new AnimatorListenerAdapter()
			{
				@Override
				public void onAnimationEnd(Animator animation)
				{
					originalView.setVisibility(show
							? View.GONE
							: View.VISIBLE);
				}
			});
		}
		else
		{
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			spinner.setVisibility(show ? View.VISIBLE : View.GONE);
			originalView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
