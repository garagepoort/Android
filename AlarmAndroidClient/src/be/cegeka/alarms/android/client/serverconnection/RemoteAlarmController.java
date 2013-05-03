package be.cegeka.alarms.android.client.serverconnection;

import static be.cegeka.alarms.android.client.futureimplementation.FutureService.whenResolved;
import java.util.List;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.FutureService;
import be.cegeka.alarms.android.client.futureimplementation.ResultCode;
import be.cegeka.alarms.android.client.futureimplementation.futurecallables.HandleAuthenticateUser;
import be.cegeka.alarms.android.client.serverconnection.login.AuthenticateTask;
import be.cegeka.alarms.android.client.serverconnection.login.LoginUserTask;
import be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync.GetAlarmsTask;
import be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync.RegisterSenderIDTask;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;


public class RemoteAlarmController {
	//TODO FIX THIS
	private Future<UserTO> future = new Future<UserTO>();
	
	public Future<List<AlarmTO>> getAllAlarms(UserTO userto) {
		return new GetAlarmsTask().executeFuture(userto.getEmail());
	}

	public Future<UserTO> loginUser(final String email, final String password) {
		boolean succes = false;
		Future<Boolean> authenticateResult = new AuthenticateTask().executeFuture(email, password);
		whenResolved(authenticateResult, new FutureCallable<Boolean>()
		{
			
			@Override
			public void onSucces(Boolean result, ResultCode resultCode)
			{
				if(resultCode == ResultCode.SUCCESS){
					future = new LoginUserTask().executeFuture(email, password);
					return future;
				}else{
					future.setValue(null, resultCode);
				}
			}

			@Override
			public void onError(Exception e)
			{
				//TODO error implementatie
			}
		});
	}
	
	public Future<Boolean> registerUser(String user, String gcmID){
		return new RegisterSenderIDTask().executeFuture(user, gcmID);
	}
	
	
	
}
