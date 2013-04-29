package be.cegeka.alarms.android.client.infrastructure;

import java.io.IOException;

import android.content.Context;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;


public class LoginController
{

	private UserPersistController persistController;
	private Context context;


	public LoginController(Context context)
	{
		this.context = context;
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


	public void logOutUser()
	{
		try
		{
			persistController.deleteUser();
		}
		catch (IOException e)
		{
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
