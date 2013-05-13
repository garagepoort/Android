package be.cegeka.alarms.android.client.infrastructure;

import java.io.IOException;

import android.content.Context;
import be.cegeka.alarms.android.client.exception.DatabaseException;
import be.cegeka.alarms.android.client.localAlarmSync.AlarmToAndroidScheduler;
import be.cegeka.alarms.android.client.localAlarmSync.LocalToAndroidAlarmSyncer;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.android.alarms.transferobjects.UserTO;


public class LoginController
{

	private UserPersistController persistController;


	public LoginController(Context context)
	{
		persistController = new UserPersistController(context);
	}


	public UserTO getLoggedInUser()
	{
		return persistController.loadUser();
	}


	public boolean isUserLoggedIn()
	{
		UserTO u = getLoggedInUser();
		return u != null;
	}


	public void logOutUser(Context context)
	{
		try
		{
			LocalToAndroidAlarmSyncer localToAndroidAlarmSyncer = new LocalToAndroidAlarmSyncer(context);
			localToAndroidAlarmSyncer.unscheduleAllAlarms();
			LocalAlarmRepository repository = new LocalAlarmRepository(context);
			repository.deleteAllAlarms();
			persistController.deleteUser();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (DatabaseException e) {
			e.printStackTrace();
		}
	}


	public void logInUser(UserTO u)
	{
		try
		{
			persistController.saveUser(u);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
