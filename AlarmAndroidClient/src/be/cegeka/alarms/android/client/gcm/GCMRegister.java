package be.cegeka.alarms.android.client.gcm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.FutureService;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.serverconnection.RemoteAlarmController;
import be.cegeka.android.alarms.transferobjects.UserTO;

import com.google.android.gcm.GCMRegistrar;


public class GCMRegister
{

	private UserTO userTO;
	public void registerWithGCMServer(final Context context)
	{
		userTO = new LoginController(context).getLoggedInUser();
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String regId = GCMRegistrar.getRegistrationId(context);
		if (regId.equals(""))
		{
			Toast.makeText(context, "registering with gcm", Toast.LENGTH_LONG).show();
			GCMRegistrar.register(context, "362183860979");
		}
		//TODO This is not ok. We have to force login before regestering.
		else if(userTO != null && !regId.equals(userTO.getGCMid()))
		{
			Future<Boolean> future = new RemoteAlarmController().registerUser(userTO.getEmail(), regId);
			FutureService.whenResolved(future, new FutureCallable<Boolean>()
			{

				@Override
				public void onSucces(Boolean result)
				{
					Toast.makeText(context, "REGISTERD SENDERID", Toast.LENGTH_SHORT).show();
					System.out.println("REGISTERED WITH WEBSERVICE!!!");
				}


				@Override
				public void onError(Exception e)
				{
					// TODO Auto-generated method stub

				}
			});
			Toast.makeText(context, "already registered", Toast.LENGTH_LONG).show();
			Log.v("", "Already registered");
		}
	}
}
