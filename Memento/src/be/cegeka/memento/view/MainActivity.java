package be.cegeka.memento.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import be.cegeka.memento.R;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.presenter.Presenter;

public class MainActivity extends Activity {
	
	private Presenter presenter;

	public void goToContactsList(View view) {
		Intent intent = new Intent(this, ContactsListActivity.class);
		startActivity(intent);
	}

	public void goToMyContactDetails(View view) {
		Contact ik = presenter.getPersoonlijkContact();
		Intent intent = new Intent(this, ContactDetailsActivity.class);
		intent.putExtra("contact", ik);
		intent.putExtra("persoonlijkContact", true);
		startActivity(intent);
	}

	public void goToTagsList(View view) {
		Intent intent = new Intent(this, TagsListActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		presenter = new Presenter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
