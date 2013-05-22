package be.cegeka.alarms.android.client.domain.models;

import be.cegeka.alarms.android.client.shouldertap.events.LoginErrorEvent;
import be.cegeka.alarms.android.client.shouldertap.events.LoginEvent;
import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class LoginModel extends Tapper {

	private static final LoginModel instance = new LoginModel();
	private UserTO loggedInUser;
	
	private LoginModel() {}
	
	public static LoginModel getInstance(){
		return instance;
	}

	public UserTO getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserTO loggedInUser) {
		this.loggedInUser = loggedInUser;
		LoginEvent event = new LoginEvent();
		event.setData(loggedInUser);
		tapShoulders(event);
	}
	
	public void setException(Exception exception){
		LoginErrorEvent event = new LoginErrorEvent();
		event.setData(exception);
		tapShoulders(event);
	}
}
