package be.cegeka.memento.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.model.ContactListUpdatedEvent;
import be.cegeka.memento.model.ContactsModel;
import be.cegeka.memento.presenter.Presenter;

public class ContactsListActivity extends Activity {

	private Presenter presenter;
	private ListView listViewContacts;
	private ContactsListShoulder contactsListShoulder;
	private boolean sendView;

	public void goToNewContactDetails(View view) {
		Intent intent = new Intent(this, ContactDetailsActivity.class);
		startActivity(intent);
	}

	public void goToContactDetails(int index) {
		ListView listView = (ListView) findViewById(R.id.listView_contacts);
		Contact contact = (Contact) listView.getItemAtPosition(index);
		Intent intent = new Intent(this, ContactDetailsActivity.class);
		intent.putExtra("contact", contact);
		intent.putExtra("persoonlijkContact", false);
		startActivity(intent);
	}

	public void onSendContactsClicked(View view) {
		if (sendView) {
			Toast.makeText(this, "verzonden", Toast.LENGTH_SHORT).show();
			ListAdapter adapter = new ArrayAdapter<Contact>(ContactsListActivity.this, android.R.layout.simple_list_item_1, ContactsModel.getInstance().getContacts());
			listViewContacts.setAdapter(adapter);
			sendView = false;

		} else {
			ListAdapter adapter = new ArrayAdapter<Contact>(ContactsListActivity.this, android.R.layout.simple_list_item_checked, ContactsModel.getInstance().getContacts());
			listViewContacts.setAdapter(adapter);
			sendView = true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_list);
		setupActionBar();
		initialize();
	}

	private void initialize() {
		sendView = false;
		listViewContacts = (ListView) findViewById(R.id.listView_contacts);
		contactsListShoulder = new ContactsListShoulder();
		ContactsModel.getInstance().addShoulder(contactsListShoulder);

		addOnListItemClickedListener();

		presenter = new Presenter();
		presenter.getContacts();
	}

	public void addOnListItemClickedListener() {
		listViewContacts.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int intdex, long looong) {
				if(sendView){
				CheckedTextView checkedTextView = (CheckedTextView) view;
				checkedTextView.setChecked(!checkedTextView.isChecked());
				}
				else{
					goToContactDetails(intdex);
				}
			}
		});
	}

	private class ContactsListShoulder extends Shoulder<ContactListUpdatedEvent> {
		public ContactsListShoulder() {
			super(ContactListUpdatedEvent.class);
		}

		@Override
		public void update(ContactListUpdatedEvent event) {
			ListAdapter adapter = new ArrayAdapter<Contact>(ContactsListActivity.this, android.R.layout.simple_list_item_1, event.getData());
			listViewContacts.setAdapter(adapter);
		}

	}

	@Override
	protected void onDestroy() {
		ContactsModel.getInstance().removeShoulder(contactsListShoulder);
		super.onDestroy();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contacts_list, menu);
		return true;
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

}
