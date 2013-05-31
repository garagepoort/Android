package be.cegeka.memento.view;

import static be.cegeka.memento.view.Toast.showBlueToast;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.events.ContactListUpdatedEvent;
import be.cegeka.memento.domain.events.SendContactsEvent;
import be.cegeka.memento.domain.shoulders.ErrorShoulder;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;
import be.cegeka.memento.facade.Facade;
import be.cegeka.memento.model.ContactsModel;
import be.cegeka.memento.model.TagsModel;


@SuppressLint("ShowToast")
public class ContactsListActivity extends Activity
{
	private Facade facade;
	private ListView listViewContacts;
	private ContactsListShoulder contactsListShoulder;
	private ContactsSentShoulder contactsSentShoulder;
	private ErrorShoulder errorShoulder;
	private boolean sendView;
	private Button sendContactsButton;
	private Toast toast;


	public void goToContactDetails(int index)
	{
		if (index == 0)
		{
			Intent intent = new Intent(this, ContactDetailsActivity.class);
			startActivity(intent);
		}
		else
		{
			ListView listView = (ListView) findViewById(R.id.listView_contacts);
			Contact contact = (Contact) listView.getItemAtPosition(index);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contact.getId()));
			intent.setData(uri);
			this.startActivity(intent);
		}
	}


	public void onSendContactsClicked(View view)
	{
		if (sendView)
		{
			if (getCheckedContacts().size() > 0)
			{
				DialogCreator.showEditableDropdownDialog(this, new DialogOKedListener<String>()
				{
					@Override
					public void okayed(String input)
					{
						sendContacts(input);
					}
				}, TagsModel.getInstance().getGroups());
			}
			else
			{
				switchContactsListItems();
				sendContactsButton.setText(R.string.contactsList_activity_button_send_contacts);
			}
		}
		else
		{
			sendContactsButton.setText(R.string.contactsList_activity_button_cancel_send_contacts);
			switchContactsListItems();
		}
	}


	private List<Contact> getCheckedContacts()
	{
		List<Contact> contacts = new ArrayList<Contact>();
		SparseBooleanArray checked = listViewContacts.getCheckedItemPositions();
		for (int i = 0; i < checked.size(); i++)
		{
			if (checked.valueAt(i))
			{
				contacts.add((Contact) listViewContacts.getItemAtPosition(checked.keyAt(i)));
			}
		}
		return contacts;
	}


	@SuppressLint("NewApi")
	private void switchContactsListItems()
	{
		int listItem = sendView ? android.R.layout.simple_list_item_1 : android.R.layout.simple_list_item_checked;
		if (sendView)
		{
			sendContactsButton.getBackground().setColorFilter(null);
		}
		else
		{
			sendContactsButton.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.OVERLAY);
		}
		ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(ContactsListActivity.this, listItem, ContactsModel.getInstance().getContacts());
		listViewContacts.setAdapter(adapter);
		sendView = !sendView;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_list);
		setupActionBar();
		try
		{
			initialize();
		}
		catch (ContactException exception)
		{
			exception.printStackTrace();
			DialogCreator.showErrorDialog(exception.getMessage(), this);
		}
	}


	@SuppressLint("InlinedApi")
	private void sendContacts(String tag)
	{
		try
		{
			if (facade.isValidTagForSending(tag))
			{
				String[] splittedTag = tag.split(" ");
				facade.sendContacts(getCheckedContacts(), splittedTag[0]);
				toast = showBlueToast(this, getString(R.string.toast_send_contacts_trying));
				switchContactsListItems();
			}
			else
			{
				onSendContactsClicked(null);
				toast.cancel();
				toast = showBlueToast(ContactsListActivity.this, getString(R.string.toast_tag_invalid_input));
			}
		}
		catch (ContactException e)
		{
			toast.cancel();
			toast = showBlueToast(this, getString(R.string.toast_something_went_wrong_retrieving_contact_info));
			e.printStackTrace();
		}
	}


	private void initialize() throws ContactException
	{
		facade = new Facade(this);
		toast = Toast.makeText(this, "", 0);
		sendView = false;
		listViewContacts = (ListView) findViewById(R.id.listView_contacts);
		listViewContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		sendContactsButton = (Button) findViewById(R.id.button_send_contacts);
		contactsListShoulder = new ContactsListShoulder();
		ContactsModel.getInstance().addShoulder(contactsListShoulder);
		contactsSentShoulder = new ContactsSentShoulder();
		facade.addShoulder(contactsSentShoulder);
		errorShoulder = new ErrorShoulder(this);
		facade.addShoulder(errorShoulder);
		addOnListItemClickedListener();
		facade.getContacts();
	}


	public void addOnListItemClickedListener()
	{
		listViewContacts.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int intdex, long looong)
			{
				if (!sendView)
				{
					goToContactDetails(intdex);
				}
				else
				{
					if (getCheckedContacts().size() > 0)
					{
						sendContactsButton.setText(getString(R.string.contactsList_activity_button_send_contacts));
					}
					else
					{
						sendContactsButton.setText(getString(R.string.contactsList_activity_button_cancel_send_contacts));
					}
				}
			}
		});
	}


	private class ContactsListShoulder extends Shoulder<ContactListUpdatedEvent>
	{
		public ContactsListShoulder()
		{
			super(ContactListUpdatedEvent.class);
		}


		@Override
		public void update(final ContactListUpdatedEvent event)
		{
			ContactsListActivity.this.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					ListAdapter adapter = new ArrayAdapter<Contact>(ContactsListActivity.this, android.R.layout.simple_list_item_1, event.getData());
					listViewContacts.setAdapter(adapter);
				}
			});
		}
	}


	private class ContactsSentShoulder extends Shoulder<SendContactsEvent>
	{
		public ContactsSentShoulder()
		{
			super(SendContactsEvent.class);
		}


		@SuppressLint("InlinedApi")
		@Override
		public void update(SendContactsEvent event)
		{
			toast.cancel();
			showBlueToast(ContactsListActivity.this, getString(R.string.toast_send_contacts_sent));
		}
	}


	@Override
	protected void onDestroy()
	{
		ContactsModel.getInstance().removeShoulder(contactsListShoulder);
		facade.removeShoulder(contactsSentShoulder);
		facade.removeShoulder(errorShoulder);
		super.onDestroy();
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.contacts_list, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
