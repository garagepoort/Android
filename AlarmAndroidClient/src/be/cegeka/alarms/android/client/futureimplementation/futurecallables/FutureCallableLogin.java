package be.cegeka.alarms.android.client.futureimplementation.futurecallables;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.activities.DialogCreator;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.FutureService;
import be.cegeka.alarms.android.client.gcm.GCMRegister;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class FutureCallableLogin implements FutureCallable<UserTO>
{
	private LoginActivity activity;
	private GCMRegister gcmRegister;

	public FutureCallableLogin(LoginActivity activity){
		this.gcmRegister=new GCMRegister();
		this.activity=activity;
	}

	@Override
	public void onSucces(UserTO result)
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
						public void onSucces(ArrayList<AlarmTO> result)
						{
							try
							{
								new LocalAlarmRepository(activity).replaceAll(result);
							}
							catch (TechnicalException e)
							{
								e.printStackTrace();
							}
							Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onError(Exception e)
						{
							// TODO Auto-generated method stub
						}
					});
			activity.showProgress(false);
			activity.startActivity(new Intent(activity.getApplicationContext(), InfoActivity.class));
	}

	@Override
	public void onError(Exception e)
	{
		DialogCreator.buildAndShowDialog(activity.getString(R.string.error_title_general), e.getMessage(), activity);
		activity.showProgress(false);
	}

}
