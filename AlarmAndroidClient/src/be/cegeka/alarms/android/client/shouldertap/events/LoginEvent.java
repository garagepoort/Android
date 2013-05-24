package be.cegeka.alarms.android.client.shouldertap.events;

import be.cegeka.android.ShouldrTap.AbstractEvent;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class LoginEvent extends AbstractEvent<UserTO>{

	private UserTO userTO;
	@Override
	public UserTO getData() {
		return userTO;
	}

	@Override
	public void setData(UserTO userTO) {
		this.userTO = userTO;
	}

}
