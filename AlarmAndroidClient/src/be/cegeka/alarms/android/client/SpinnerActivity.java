package be.cegeka.alarms.android.client;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SpinnerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spinner, menu);
		return true;
	}

}
