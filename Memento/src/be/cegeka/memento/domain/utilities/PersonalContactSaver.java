package be.cegeka.memento.domain.utilities;

import java.io.IOException;
import android.content.Context;
import android.content.SharedPreferences;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;


public class PersonalContactSaver
{
	private Context context;


	public PersonalContactSaver(Context context)
	{
		this.context = context;
	}


	/**
	 * Save the {@link User} from the {@link SharedPreferences}
	 * 
	 * @param contact
	 *            The {@link User} to be saved in the {@link SharedPreferences}.
	 * @throws IOException
	 */
	public void saveContact(Contact contact) throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("id", contact.getId());
		editor.putString("naam", contact.getNaam());
		editor.putString("email", contact.getEmail());
		editor.putString("tel", contact.getTel());
		editor.commit();
	}


	/**
	 * Load the {@link User} from the {@link SharedPreferences}
	 * 
	 * @return A {@link User} if the user was saved else null
	 */
	public Contact loadContact()
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		if (!(settings.contains("id") || settings.contains("naam") || settings.contains("email") || settings.contains("tel")))
		{
			return null;
		}
		long id = settings.getLong("id", 0);
		String naam = settings.getString("naam", "");
		String email = settings.getString("email", "");
		String tel = settings.getString("tel", "");
		Contact contact = null;
		try
		{
			contact = new Contact(naam, email, tel);
		}
		catch (ContactException e)
		{
			e.printStackTrace();
		}
		contact.setId(id);
		return contact;
	}


	/**
	 * Delete the User from the {@link SharedPreferences}.
	 * 
	 * @param ctx
	 *            The {@link Context}.
	 * @throws IOException
	 */
	public void deleteContact() throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}
