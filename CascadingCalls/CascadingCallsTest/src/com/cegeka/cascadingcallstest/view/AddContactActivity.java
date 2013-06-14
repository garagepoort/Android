package com.cegeka.cascadingcallstest.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cegeka.cascadingcallstest.model.Contact;
import com.example.cascadingcallstest.R;

public class AddContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveContact(View view){
		EditText namefield = (EditText) findViewById(R.id.nameField);
		EditText numberfield = (EditText) findViewById(R.id.Numberfield);
		Contact contact = new Contact(numberfield.getText().toString(), namefield.getText().toString());
		Intent resultIntent = new Intent();
		resultIntent.putExtra("contact", contact);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
		
	}
	
	public void cancelAddContact(View view){
		setResult(RESULT_CANCELED);
		finish();
	}

}
