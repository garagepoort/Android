package be.cegeka.alarms.android.client.serverconnection;

import java.util.List;

import be.cegeka.alarms.android.client.serverconnection.login.LoginUserTask;
import be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync.GetAlarmsTask;
import be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync.RegisterSenderIDTask;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;


public class RemoteAlarmController
{

	public Future<List<AlarmTO>> getAllAlarms(UserTO userto)
	{
		return new GetAlarmsTask().executeFuture(userto.getEmail());
	}


	public Future<UserTO> loginUser(final String email, final String password)
	{
		return new LoginUserTask().executeFuture(email, password);
	}


	public Future<Boolean> registerUser(String email, String gcmID)
	{
		return new RegisterSenderIDTask().executeFuture(email, gcmID);
	}


	public Future<Boolean> unregisterUser(String email) {
		return new UnregisterSenderIDTask().executeFuture(email);
	}

}
