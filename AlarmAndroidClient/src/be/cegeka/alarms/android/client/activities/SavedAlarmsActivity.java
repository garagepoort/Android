package be.cegeka.alarms.android.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import be.cegeka.alarms.android.client.R;
public class SavedAlarmsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_alarms);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_alarms, menu);
		return true;
	}

}
