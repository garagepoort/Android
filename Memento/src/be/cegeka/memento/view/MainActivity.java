package be.cegeka.memento.view;

import static be.cegeka.memento.domain.utilities.IPConfigurator.configureIPAddress;
import static be.cegeka.memento.view.DialogCreator.showErrorDialog;
import static be.cegeka.memento.view.Toast.showBlueToast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.events.TagEvent;
import be.cegeka.memento.domain.gcm.GCMRegister;
import be.cegeka.memento.domain.shoulders.ErrorShoulder;
import be.cegeka.memento.domain.utilities.InternetChecker;
import be.cegeka.memento.facade.Facade;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends Activity
{
	private Facade facade;
	private ErrorShoulder errorShoulder;
	private TagShoulder tagShoulder;
	private android.widget.Toast toast;


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


	public void openScanner(View view)
	{
		facade.openScanner(this);
	}


	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		toast = showBlueToast(this, getString(R.string.toast_add_to_tag_trying));

		if (intent != null)
		{
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			if (scanResult != null && scanResult.getContents().startsWith("memento://be.cegeka.memento.tag/#"))
			{
				String tag = scanResult.getContents().split("#")[1];
				if (facade.isValidTag(tag))
				{
					facade.addToTag(tag);
				}
				else
				{
					toast.cancel();
					showBlueToast(getApplicationContext(), getString(R.string.toast_tag_invalid_input));
				}
			}
			else if (scanResult != null && scanResult.getContents().startsWith("memento://be.cegeka.memento.contact/#"))
			{
				System.out.println(scanResult.getContents());
			}
			else
			{
				toast.cancel();
				showErrorDialog(getString(R.string.dialog_qr_show_no_tag_message), this);
			}
		}
		else
		{
			toast.cancel();
			showErrorDialog(getString(R.string.dialog_qr_show_no_tag_message), this);
		}
	}


	@SuppressLint("NewApi")
	private class TagShoulder extends Shoulder<TagEvent>
	{
		public TagShoulder()
		{
			super(TagEvent.class);
		}


		@Override
		public void update(TagEvent event)
		{
			toast.cancel();
			showBlueToast(MainActivity.this, event.getData());
			facade.getTags();
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new GCMRegister().registerWithGCMServer(this);

		facade = new Facade(this);
		facade.getTags();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_settings)
		{
			configureIPAddress(this, item);
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onStart()
	{
		InternetChecker internetChecker = new InternetChecker();
		errorShoulder = new ErrorShoulder(this);
		tagShoulder = new TagShoulder();
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
		facade.addShoulder(errorShoulder);
		facade.addShoulder(tagShoulder);
		super.onStart();
	}


	@Override
	protected void onDestroy()
	{
		facade.removeShoulder(errorShoulder);
		facade.removeShoulder(tagShoulder);
		super.onDestroy();
	}

}
