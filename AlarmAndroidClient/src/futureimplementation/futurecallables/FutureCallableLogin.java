package futureimplementation.futurecallables;

import java.util.ArrayList;
import java.util.List;
import synchronisation.RemoteAlarmController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.activities.DialogCreator;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.gcm.GCMRegister;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import futureimplementation.Future;
import futureimplementation.FutureCallable;
import futureimplementation.FutureService;
import futureimplementation.ResultCode;

public class FutureCallableLogin implements FutureCallable<UserTO>
{
	private LoginActivity activity;
	private GCMRegister gcmRegister;

	public FutureCallableLogin(LoginActivity activity){
		this.gcmRegister=new GCMRegister();
		this.activity=activity;
	}

	@Override
	public void apply(UserTO result, ResultCode code)
	{
		if (code == ResultCode.SUCCESS)
		{
			gcmRegister.registerWithGCMServer(activity);
			LoginController loginController = new LoginController(activity);
			loginController.logInUser(result);
			Toast.makeText(activity, "Login succesfull", Toast.LENGTH_LONG).show();

			Future<List<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(result);
			FutureService.whenResolved(future,
					new FutureCallable<ArrayList<AlarmTO>>()
					{
						@Override
						public void apply(ArrayList<AlarmTO> result, ResultCode resultCode)
						{
							new LocalAlarmRepository(activity).replaceAll(result);
							Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show();
						}
					});
			activity.startActivity(new Intent(activity.getApplicationContext(), InfoActivity.class));

		}
		else
		{
			DialogCreator.buildAndShowErrorDialog(code.toString(), activity);
			activity.showProgress(false);
		}
	}

}
