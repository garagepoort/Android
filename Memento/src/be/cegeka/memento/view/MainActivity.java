package be.cegeka.memento.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.gcm.GCMRegister;
import be.cegeka.memento.domain.utilities.InternetChecker;
import be.cegeka.memento.facade.Facade;


public class MainActivity extends Activity
{
	private Facade facade;


	public void goToContactsList(final View view)
	{
		if (facade.getPersoonlijkContact() == null)
		{
			DialogCreator.showOptionsDialog(getString(R.string.dialog_error_title), getString(R.string.toast_add_personal_contact), getResources().getDrawable(android.R.drawable.ic_dialog_alert), this, new DialogOKedListener<Boolean>()
			{
				@Override
				public void okayed(Boolean okayed)
				{
					if (okayed)
					{
						goToMyContactDetails(view);
					}
				}
			});
		}
		else
		{
			Intent intent = new Intent(this, ContactsListActivity.class);
			startActivity(intent);
		}
	}


	public void goToMyContactDetails(View view)
	{
		Intent intent = new Intent(this, ContactDetailsActivity.class);
		startActivity(intent);
	}


	public void goToTagsList(View view)
	{
		Intent intent = new Intent(this, TagsListActivity.class);
		startActivity(intent);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		facade = new Facade(this);
		facade.getTags();
		new GCMRegister().registerWithGCMServer(this);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	protected void onStart()
	{
		InternetChecker internetChecker = new InternetChecker();
		if (!internetChecker.isNetworkAvailable(this))
		{
			DialogCreator.showErrorDialog(getString(R.string.dialog_no_internet), this, new DialogOKedListener<Void>()
			{
				@Override
				public void okayed(Void input)
				{
					MainActivity.this.finish();
				}
			});
		}
		super.onStart();
	}
}
