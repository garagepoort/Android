package synchronisation;

import java.util.ArrayList;

import synchronisation.remote.RemoteDBSoapRequest;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;
import futureimplementation.Future;


public class RemoteAlarmController {

	public Future<ArrayList<AlarmTO>> getAllAlarms(UserTO userto) {
		Future<ArrayList<AlarmTO>> future = new Future<ArrayList<AlarmTO>>();
		RemoteDBSoapRequest dbSoapRequest = new RemoteDBSoapRequest(future,null);
		dbSoapRequest.execute(RemoteDBSoapRequest.GET_ALARMS_FROM_USER, userto.getEmail());
		return future;
	}

	public Future<UserTO> loginUser(String mEmail, String mPassword) {
		Future<UserTO> future = new Future<UserTO>();
		RemoteDBSoapRequest dbSoapRequest = new RemoteDBSoapRequest(null, future);
		dbSoapRequest.execute(RemoteDBSoapRequest.LOGIN, mEmail, mPassword);
		return future;
	}
}
