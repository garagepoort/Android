package be.cegeka.memento.domain.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
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
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		int rawContactInsertIndex = ops.size();
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null)
				.build());

		

		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
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
		
		try {
			System.out.println("bezig met opslaan");
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			System.out.println("opgeslagen");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

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
		
		String WHERE_CONDITION = ContactsContract.Data.MIMETYPE + " = '" +   ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";
	    String[] PROJECTION = {ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.CONTACT_ID};
	    String SORT_ORDER = ContactsContract.Data.DISPLAY_NAME;

	    Cursor cur = context.getContentResolver().query(
	            ContactsContract.Data.CONTENT_URI,
	            PROJECTION,
	            WHERE_CONDITION,
	            null,
	            SORT_ORDER);
	    
	    while(cur.moveToNext()){
	    	String displayName = cur.getString(0);
	    	String contactID = cur.getString(1);
	    	Contact c = new Contact();
			c.setId(Long.valueOf(contactID));
			c.setNaam(displayName);
			contacts.add(c);
	    }
	    cur.close();
		
		Collections.sort(contacts);
		return contacts;
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

}
