package be.cegeka.alarms.android.client.domain.infrastructure;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import be.cegeka.android.alarms.transferobjects.UserTO;


public class UserPersistence
{
	private Context context;


	public UserPersistence(Context context)
	{
		this.context = context;
	}


	/**
	 * Save the {@link User} from the {@link SharedPreferences}
	 * 
	 * @param user
	 *            The {@link User} to be saved in the {@link SharedPreferences}.
	 * @throws IOException
	 */
	public void saveUser(UserTO user) throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("id", user.getUserid());
		editor.putString("naam", user.getNaam());
		editor.putString("achternaam", user.getAchternaam());
		editor.putString("email", user.getEmail());
		editor.putString("paswoord", user.getPaswoord());
		editor.putBoolean("admin", user.isAdmin());
		editor.putString("GCMid", user.getGCMid());
		editor.commit();
	}


	/**
	 * Load the {@link User} from the {@link SharedPreferences}
	 * 
	 * @return A {@link User} if the user was saved else null
	 */
	public UserTO loadUser()
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);

		if (!(settings.contains("id") || settings.contains("naam") || settings.contains("achternaam") || settings.contains("email") || settings.contains("paswoord")))
		{
			return null;
		}
		int id = settings.getInt("id", 0);
		String naam = settings.getString("naam", "");
		String achternaam = settings.getString("achternaam", "");
		String email = settings.getString("email", "");
		String paswoord = settings.getString("paswoord", "");
		boolean admin = settings.getBoolean("admin", false);
		String gcmid = settings.getString("GCMid", "");
		UserTO user = new UserTO(id, naam, achternaam, email, admin, gcmid);
		user.setPaswoord(paswoord);

		return user;
	}


	/**
	 * Delete the User from the {@link SharedPreferences}.
	 * 
	 * @param ctx
	 *            The {@link Context}.
	 * @throws IOException
	 */
	public void deleteUser() throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}

}
