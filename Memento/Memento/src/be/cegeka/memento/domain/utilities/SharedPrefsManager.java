package be.cegeka.memento.domain.utilities;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsManager
{
	public static String getSharedPreference(Context context, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("file", 0);
		return sharedPreferences.getString(key, "");
	}


	public static String getSharedPreference(Context context, String key, String defaultValue)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("file", 0);
		return sharedPreferences.getString(key, defaultValue);
	}


	public static void saveSharedPreference(Context context, String key, String newValue)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("file", 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, newValue);
		editor.commit();
	}
}
