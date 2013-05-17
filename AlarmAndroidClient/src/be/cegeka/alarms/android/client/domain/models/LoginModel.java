package be.cegeka.alarms.android.client.domain.models;

import java.util.Observable;

import be.cegeka.android.alarms.transferobjects.UserTO;

public class LoginModel extends Observable {

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
		setChanged();
		notifyObservers(loggedInUser);
	}
}
