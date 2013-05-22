package be.cegeka.alarms.android.client.domain.login;

import java.io.IOException;

import android.content.Context;
import be.cegeka.alarms.android.client.domain.alarmSync.AlarmSyncer;
import be.cegeka.alarms.android.client.domain.alarmSync.localAlarmSync.LocalToAndroidAlarmSyncer;
import be.cegeka.alarms.android.client.domain.controllers.LocalAlarmRepository;
import be.cegeka.alarms.android.client.domain.controllers.ServerCalls;
import be.cegeka.alarms.android.client.domain.exception.DatabaseException;
import be.cegeka.alarms.android.client.domain.gcm.GCMRegister;
import be.cegeka.alarms.android.client.domain.infrastructure.UserPersistence;
import be.cegeka.alarms.android.client.domain.models.LoginModel;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;

public class LoginController {

	private UserPersistence persistController;
	private Context context;

	public LoginController(Context context) {
		this.context = context;
		persistController = new UserPersistence(context);
	}

	public UserTO getLoggedInUser() {
		return persistController.loadUser();
	}

	public boolean isUserLoggedIn() {
		UserTO u = getLoggedInUser();
		return u != null;
	}

	public void logOutUser() {
		try {
			LocalToAndroidAlarmSyncer localToAndroidAlarmSyncer = new LocalToAndroidAlarmSyncer(context);
			localToAndroidAlarmSyncer.unscheduleAllAlarms();
			LocalAlarmRepository repository = new LocalAlarmRepository(context);
			repository.deleteAllAlarms();
			new GCMRegister().unregister(context);
			persistController.deleteUser();
			LoginModel.getInstance().setLoggedInUser(null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void logInUser(String email, String password) {
		final Future<UserTO> returnFuture = new Future<UserTO>();
		Future<UserTO> future = new ServerCalls(context).checkUserCredentials(email, password);
		Future.whenResolved(future, new FutureCallable<UserTO>() {

			@Override
			public void onSucces(final UserTO result) {
				try {
					persistController.saveUser(result);
					new GCMRegister().registerWithGCMServer(context);
					new AlarmSyncer().syncAllAlarms(context);
					LoginModel.getInstance().setLoggedInUser(result);
					
				} catch (IOException e1) {
					LoginModel.getInstance().setException(e1);
					e1.printStackTrace();
				}
			}

			@Override
			public void onError(Exception e) {
				LoginModel.getInstance().setException(e);
				e.printStackTrace();
			}
		});

	}
}
