package com.cegeka.alarmmanager.utilities;

import java.io.IOException;

import android.content.Context;
import be.cegeka.android.alarms.transferobjects.UserTO;

public final class UserLoginLogOut {

	public static UserTO getLoggedInUser(Context ctx) {
		return UserLoaderSaver.loadUser(ctx);
	}

	public static boolean userLoggedIn(Context context) {
		UserTO u = getLoggedInUser(context);
		return u != null;
	}

	public static void logOutUser(Context ctx) {
		try {
			UserLoaderSaver.deleteUser(ctx);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logInUser(Context ctx, UserTO u) {
		try {
			UserLoaderSaver.saveUser(ctx, u);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
