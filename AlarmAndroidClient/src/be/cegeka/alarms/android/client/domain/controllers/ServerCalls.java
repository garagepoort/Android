package be.cegeka.alarms.android.client.domain.controllers;

import java.util.List;

import android.content.Context;
import be.cegeka.alarms.android.client.domain.alarmSync.remoteAlarmSync.GetAlarmsTask;
import be.cegeka.alarms.android.client.domain.alarmSync.remoteAlarmSync.RegisterSenderIDTask;
import be.cegeka.alarms.android.client.domain.alarmSync.remoteAlarmSync.UnregisterSenderIDTask;
import be.cegeka.alarms.android.client.domain.login.LoginUserTask;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;

public class ServerCalls {
	private Context context;

	public ServerCalls(Context context) {
		this.context = context;
	}

	public Future<List<AlarmTO>> getAllAlarms(UserTO userto) {
		return new GetAlarmsTask(context).executeFuture(userto.getEmail());
	}

	public Future<UserTO> checkUserCredentials(final String email, final String password) {
		return new LoginUserTask(context).executeFuture(email, password);
	}

	public Future<Boolean> registerUser(String email, String gcmID) {
		return new RegisterSenderIDTask(context).executeFuture(email, gcmID);
	}

	public Future<Boolean> unregisterUser(String registrationID) {
		return new UnregisterSenderIDTask(context).executeFuture(registrationID);
	}

}
