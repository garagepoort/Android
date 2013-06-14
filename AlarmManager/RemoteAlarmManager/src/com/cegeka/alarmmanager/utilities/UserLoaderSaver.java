package com.cegeka.alarmmanager.utilities;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import be.cegeka.android.alarms.transferobjects.UserTO;


class UserLoaderSaver
{

	/**
	 *  Save the {@link User} from the {@link SharedPreferences}
	 * 
	 * @param user The {@link User} to be saved in the {@link SharedPreferences}.
	 * @throws IOException
	 */
	public static void saveUser(Context ctx, UserTO user) throws IOException
	{
		SharedPreferences settings = ctx.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("id", user.getUserid());
		editor.putString("naam", user.getPaswoord());
		editor.putString("achternaam", user.getEmail());
		editor.putString("email", user.getEmail());
		editor.putString("paswoord", user.getPaswoord());
		editor.putBoolean("admin", user.isAdmin());
		editor.commit();
	}


	/**
	 * Load the {@link User} from the {@link SharedPreferences}
	 * 
	 * @return A {@link User} if the user was saved else null
	 */
	public static UserTO loadUser(Context ctx)
	{
		SharedPreferences settings = ctx.getSharedPreferences("file", 0);
		
		if(!(settings.contains("id") || settings.contains("naam") || settings.contains("achternaam") || settings.contains("email") || settings.contains("paswoord")))
		{
			return null;
		}
		int id = settings.getInt("id", 0);
		String naam = settings.getString("naam", "");
		String achternaam = settings.getString("achternaam", "");
		String email = settings.getString("email", "");
		String paswoord = settings.getString("paswoord", "");
		boolean admin = settings.getBoolean("admin", false);
		UserTO user = new UserTO(id, naam, achternaam, email, admin);
		user.setPaswoord(paswoord);
		
		return user;
	}
	
	/**
	 * Delete the User from the {@link SharedPreferences}.
	 * @param ctx The {@link Context}.
	 * @throws IOException
	 */
	public static void deleteUser(Context ctx) throws IOException
	{
		SharedPreferences settings = ctx.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
	
}
