package be.cegeka.alarms.android.client.futures;

import static be.cegeka.android.flibture.Future.whenResolved;

import java.util.List;

import android.content.Intent;
import android.widget.Toast;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.activities.DialogCreator;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.gcm.GCMRegister;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;

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
			LoginController loginController = new LoginController(activity);
			loginController.logInUser(result);
			gcmRegister.registerWithGCMServer(activity);
			
			Toast.makeText(activity, "Login succesfull", Toast.LENGTH_LONG).show();

			Future<List<AlarmTO>> future = new RemoteAlarmController().getAllAlarms(result);
			whenResolved(future,
					new FutureCallable<List<AlarmTO>>()
					{

						@Override
						public void onSucces(List<AlarmTO> result)
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
