package synchronisation;

import java.util.List;
import synchronisation.remote.AuthenticateTask;
import synchronisation.remote.GetAlarmsTask;
import synchronisation.remote.LoginUserTask;
import synchronisation.remote.RegisterSenderIDTask;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import futureimplementation.Future;
import futureimplementation.FutureCallable;
import futureimplementation.FutureService;
import futureimplementation.ResultCode;
import futureimplementation.futurecallables.AuthenticateUserFutureCallable;


public class RemoteAlarmController {

	public Future<List<AlarmTO>> getAllAlarms(UserTO userto) {
		System.out.println(userto.getEmail());
		Future<List<AlarmTO>> future = new Future<List<AlarmTO>>();
		GetAlarmsTask alarmsTask = new GetAlarmsTask(future);
		alarmsTask.execute(userto.getEmail());
		return future;
	}

	public Future<UserTO> loginUser(final String email, final String password) {
		final Future<UserTO> future = new Future<UserTO>();
		Future<Boolean> future2 = new Future<Boolean>();
		AuthenticateTask authenticateTask = new AuthenticateTask(future2);
		authenticateTask.execute(email, password);
		FutureService.whenResolved(future2, new AuthenticateUserFutureCallable(email, password, future));
		return future;
	}
	
	public Future<Boolean> registerUser(String user, String gcmID){
		Future<Boolean> future = new Future<Boolean>();
		RegisterSenderIDTask idTask = new RegisterSenderIDTask(future);
		idTask.execute(user, gcmID);
		return future;
	}
	
	
}
