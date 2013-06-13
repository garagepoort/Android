package be.cegeka.memento.domain.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;

public class ContactsPersistence {
	private Context context;

	public ContactsPersistence(Context context) {
		this.context = context;
	}

	/**
	 * Save the {@link User} from the {@link SharedPreferences}
	 * 
	 * @param user
	 *            The {@link User} to be saved in the {@link SharedPreferences}.
	 * @throws IOException
	 */
	@SuppressLint("InlinedApi")
	public void saveContact(Contact contact) throws IOException {
		try {
			addContact(contact);
			addToGroup(Long.valueOf(getRawContactID(contact.getNaam())), ifGroup("Memento"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void addContact(Contact contact) throws RemoteException, OperationApplicationException{
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();
<<<<<<< HEAD
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI).withValue(RawContacts.ACCOUNT_TYPE, null).withValue(RawContacts.ACCOUNT_NAME, null).build());

		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex).withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE).withValue(Phone.NUMBER, contact.getTel()).build());

		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex).withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE).withValue(StructuredName.DISPLAY_NAME, contact.getNaam()).build());

		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI).withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex).withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE).withValue(Email.ADDRESS, contact.getEmail()).build());

		try {
			System.out.println("bezig met opslaan");
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			System.out.println("opgeslagen");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
=======
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null)
				.build());
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
						.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
						.withValue(Phone.NUMBER, contact.getTel())
						.build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.DISPLAY_NAME, contact.getNaam())
				.build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
				.withValue(Email.ADDRESS, contact.getEmail())
				.build());
		context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	}

	public long getRawContactID(String name){
		long id = 0;
		Cursor cur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{Data.RAW_CONTACT_ID}, Data.DISPLAY_NAME + " = '" + name + "'", null, null);
		while(cur.moveToNext()){
			id = cur.getLong(0);
		}
		cur.close();
		return id;
	}

	public void addToGroup(long personId, long groupId) {
		ContentValues values = new ContentValues();
		values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, personId);
		values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,	groupId);
		values.put(
				ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
				ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
	}

	private Long ifGroup(String name) {
		String selection = ContactsContract.Groups.DELETED + "=? and " + ContactsContract.Groups.GROUP_VISIBLE + "=?";
		String[] selectionArgs = { "0", "1" };
		Cursor cursor = context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI, null, selection, selectionArgs, null);
		cursor.moveToFirst();
		int len = cursor.getCount();

		Long GroupId = null;
		for (int i = 0; i < len; i++) {
			Long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Groups._ID));
			String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
			if (title.equals(name)) {
				GroupId = id;
				break;
			}
			cursor.moveToNext();
>>>>>>> e9ab020a279a8156c55a898b59404c3f5733da84
		}
		cursor.close();

		if(GroupId == null){
			ArrayList<ContentProviderOperation> opsGroup = new ArrayList<ContentProviderOperation>();
			opsGroup.add(ContentProviderOperation.newInsert(ContactsContract.Groups.CONTENT_URI)
					.withValue(ContactsContract.Groups.TITLE, "Memento")
					.withValue(ContactsContract.Groups.GROUP_VISIBLE, true)
					.withValue(ContactsContract.Groups.ACCOUNT_NAME, "Memento")
					.withValue(ContactsContract.Groups.ACCOUNT_TYPE, "Memento")
					.build());
			try {
				context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, opsGroup);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			GroupId = ifGroup("Memento");
		}
		return GroupId;
	}

	/**
	 * Load the {@link User} from the {@link SharedPreferences}
	 * 
	 * @return A {@link User} if the user was saved else null
	 * @throws ContactException
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> loadContacts() throws ContactException {
		List<Contact> contacts = new ArrayList<Contact>();
<<<<<<< HEAD

		String WHERE_CONDITION = ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";
		String[] PROJECTION = {ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.CONTACT_ID};
		String SORT_ORDER = ContactsContract.Data.DISPLAY_NAME;

		Cursor cur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, PROJECTION, WHERE_CONDITION, null, SORT_ORDER);

=======
		String WHERE_CONDITION = ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";
		String[] PROJECTION = { ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.CONTACT_ID };
		String SORT_ORDER = ContactsContract.Data.DISPLAY_NAME;
		Cursor cur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, PROJECTION, WHERE_CONDITION, null, SORT_ORDER);
>>>>>>> e9ab020a279a8156c55a898b59404c3f5733da84
		while (cur.moveToNext()) {
			String displayName = cur.getString(0);
			String contactID = cur.getString(1);
			Contact c = new Contact();
			c.setId(Long.valueOf(contactID));
			c.setNaam(displayName);
			contacts.add(c);
		}
		cur.close();
<<<<<<< HEAD

=======
>>>>>>> e9ab020a279a8156c55a898b59404c3f5733da84
		Collections.sort(contacts);
		return contacts;
	}

	@SuppressLint("InlinedApi")
	public List<Contact> completeContacts(List<Contact> checkedContacts) throws ContactException {
		List<Contact> result = new ArrayList<Contact>();
		for (Contact c : checkedContacts) {
			result.add(getCompleteContact(c));
		}
		return result;
	}

	public Contact getCompleteContact(Contact c) throws ContactException {
		Cursor mailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + String.valueOf(c.getId()), null, null);
		while (mailCur.moveToNext()) {
			c.setEmail(mailCur.getString(mailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
		}
		mailCur.close();
		Cursor numberCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + String.valueOf(c.getId()), null, null);
		while (numberCur.moveToNext()) {
			c.setTel(numberCur.getString(numberCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		}
		numberCur.close();
		return c;
	}

	/**
	 * Delete the User from the {@link SharedPreferences}.
	 * 
	 * @param ctx
	 *            The {@link Context}.
	 * @throws IOException
	 */
	public void deleteContact() throws IOException {
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
<<<<<<< HEAD

	@SuppressLint("InlinedApi")
	public List<Contact> completeContacts(List<Contact> checkedContacts) throws ContactException {
		for (Contact c : checkedContacts) {

			Cursor mailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
					+ String.valueOf(c.getId()), null, null);
			while (mailCur.moveToNext()) {
				c.setEmail(mailCur.getString(mailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
			}
			mailCur.close();

			Cursor numberCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
					+ String.valueOf(c.getId()), null, null);
			while (numberCur.moveToNext()) {
				c.setTel(numberCur.getString(numberCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			}
			numberCur.close();
		}

		return checkedContacts;
	}
=======
>>>>>>> e9ab020a279a8156c55a898b59404c3f5733da84
}
