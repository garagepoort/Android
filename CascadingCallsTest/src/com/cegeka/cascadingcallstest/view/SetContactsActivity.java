package com.cegeka.cascadingcallstest.view;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.cegeka.cascadingcallstest.db.ContactsLoaderSaver;
import com.cegeka.cascadingcallstest.model.Contact;
import com.example.cascadingcallstest.R;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

public class SetContactsActivity extends Activity {

	// LISTVIEW REQUIREMENTS
	private DragSortListView list;
	private ArrayAdapter<Contact> adapter;

	private DragSortController mController;

	//DRAGSORTCONTROLLER SETTINGS VARIABLES
	public int dragStartMode = DragSortController.ON_DOWN;
	public boolean removeEnabled = true;
	public int removeMode = DragSortController.CLICK_REMOVE;
	public boolean sortEnabled = true;
	public boolean dragEnabled = true;

	//DROP AND REMOVE LISTENERS
	private DragSortListView.DropListener onDrop =
			new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				Contact item = adapter.getItem(from);
				adapter.remove(item);
				adapter.insert(item, to);
			}
		}
	};

	private DragSortListView.RemoveListener onRemove = 
			new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			adapter.remove(adapter.getItem(which));
		}
	};

	//INTENT CODES
	public static final int ACTION_ADD = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_contacts);

		setupActionBar();

		adapter = new ArrayAdapter<Contact>(this, R.layout.list_item_handle_left, R.id.text, ContactsLoaderSaver.loadContacts());

		list = (DragSortListView) findViewById(R.id.contactsList);
		list.setAdapter(adapter);

		mController = buildController(list);
		list.setFloatViewManager(mController);
		list.setOnTouchListener(mController);
		list.setDragEnabled(dragEnabled);

		list.setDropListener(onDrop);
		list.setRemoveListener(onRemove);
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.set_contacts, menu);
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

	public void saveAction(View view){
		saveContactsList();
	}


	private void saveContactsList() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		for(int i= 0; i < adapter.getCount(); i++){
			contacts.add(i, adapter.getItem(i));
		}
		ContactsLoaderSaver.saveContacts(contacts);
	}

	public DragSortController buildController(DragSortListView dslv) {

		DragSortController controller = new DragSortController(dslv);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setClickRemoveId(R.id.click_remove);
		controller.setRemoveEnabled(removeEnabled);
		controller.setSortEnabled(sortEnabled);
		controller.setDragInitMode(dragStartMode);
		controller.setRemoveMode(removeMode);
		return controller;
	}

	public void startAddContact(View view){
		Intent addContactIntent = new Intent(this, AddContactActivity.class);
		startActivityForResult(addContactIntent, ACTION_ADD);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case(ACTION_ADD): 
			if(resultCode == Activity.RESULT_OK){
				Contact contact = (Contact) data.getSerializableExtra("contact");
				adapter.add(contact);
				adapter.notifyDataSetChanged();
				saveContactsList();
			}
			else if(resultCode == Activity.RESULT_CANCELED){
				//Do Nothing
			}
		break;

		}
	}

	public void returnToMain(View view){
		SetContactsActivity.this.finish();
	}

}
