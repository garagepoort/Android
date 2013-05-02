package synchronisation;

import java.util.List;
import synchronisation.remote.RemoteDBSoapRequest;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;
import futureimplementation.Future;


public class RemoteAlarmController {

	public Future<List<AlarmTO>> getAllAlarms(UserTO userto) {
		System.out.println(userto.getEmail());
		Future<List<AlarmTO>> future = new Future<List<AlarmTO>>();
		RemoteDBSoapRequest dbSoapRequest = new RemoteDBSoapRequest(future);
		dbSoapRequest.execute(RemoteDBSoapRequest.GET_ALARMS_FROM_USER, userto.getEmail());
		return future;
	}

	public Future<UserTO> loginUser(String mEmail, String mPassword) {
		Future<UserTO> future = new Future<UserTO>();
		RemoteDBSoapRequest dbSoapRequest = new RemoteDBSoapRequest(future);
		dbSoapRequest.execute(RemoteDBSoapRequest.LOGIN, mEmail, mPassword);
		return future;
	}
	
	public Future<Boolean> registerUser(String user, String gcmID){
		Future<Boolean> future = new Future<Boolean>();
		RemoteDBSoapRequest dbSoapRequest = new RemoteDBSoapRequest(future);
		dbSoapRequest.execute(RemoteDBSoapRequest.REGISTER_SENDERID, user, gcmID);
		return future;
	}
}
