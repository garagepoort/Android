package be.cegeka.memento.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.gcm.GCMRegister;
import be.cegeka.memento.domain.infrastructure.InternetChecker;
import be.cegeka.memento.presenter.Presenter;

public class MainActivity extends Activity {
	private Presenter presenter;

	public void goToContactsList(View view) {
		if (presenter.getPersoonlijkContact(this) == null) {
			goToMyContactDetails(view);
			Toast.showBlueToast(this, getString(R.string.toast_add_personal_contact));
		} else {
			Intent intent = new Intent(this, ContactsListActivity.class);
			startActivity(intent);
		}
	}

	public void goToMyContactDetails(View view) {
		Intent intent = new Intent(this, ContactDetailsActivity.class);
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
		presenter.getTags(this);
		new GCMRegister().registerWithGCMServer(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		InternetChecker internetChecker = new InternetChecker();
		if(!internetChecker.isNetworkAvailable(this)){
			DialogCreator.showErrorDialog(getString(R.string.dialog_no_internet), this, new DialogOKedListener<Void>() {
				
				@Override
				public void okayed(Void input) {
					MainActivity.this.finish();
				}
			});
		}
		super.onStart();
	}
	
	
}
