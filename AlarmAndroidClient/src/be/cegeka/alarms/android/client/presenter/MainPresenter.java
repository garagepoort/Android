package be.cegeka.alarms.android.client.presenter;

import android.content.Context;
import be.cegeka.alarms.android.client.domain.alarmSync.AlarmSyncer;
import be.cegeka.alarms.android.client.domain.login.LoginController;

public class MainPresenter {

	private Context context;

	public MainPresenter(Context context) {
		this.context = context;
	}

	public void updateAlarms() {
		new AlarmSyncer().syncAllAlarms(context);
	}
	
	public void logout(){
		LoginController loginController = new LoginController(context);
		loginController.logOutUser();
	}
	
	public void login(String email, String password){
		LoginController loginController = new LoginController(context);
		loginController.logInUser(email, password);
	}
}
