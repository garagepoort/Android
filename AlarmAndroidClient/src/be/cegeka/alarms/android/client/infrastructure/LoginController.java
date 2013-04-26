package be.cegeka.alarms.android.client.infrastructure;

import java.io.IOException;

import android.content.Context;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class LoginController {
	
	private UserPersistController persistController;
	
	
	public LoginController(){
		persistController = new UserPersistController();
	}
	public UserTO getLoggedInUser(Context ctx){
		return persistController.loadUser(ctx);
	}
	
	public boolean isUserLoggedIn(Context context){
		UserTO u = getLoggedInUser(context);
		return u != null; 
	}
	
	public void logOutUser(Context ctx){
		try {
			persistController.deleteUser(ctx);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logInUser(Context ctx, UserTO u){
		try {
			persistController.saveUser(ctx, u);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
