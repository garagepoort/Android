package be.cegeka.alarms.android.client.domain.gcm;

import static be.cegeka.android.flibture.Future.whenResolved;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import be.cegeka.alarms.android.client.domain.controllers.ServerCalls;
import be.cegeka.alarms.android.client.domain.login.LoginController;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;

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
		//TODO This is not ok. We have to force login before registering.
		else if(!regId.equals(userTO.getGCMid()))
		{
			Future<Boolean> future = new ServerCalls(context).registerUser(userTO.getEmail(), regId);
			whenResolved(future, new FutureCallable<Boolean>()
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
					e.printStackTrace();
				}
			});
			
		}else{
			Toast.makeText(context, "already registered", Toast.LENGTH_LONG).show();
			Log.v("", "Already registered");
		}
	}
	
	public void unregister(Context context){
		GCMRegistrar.unregister(context);
	}
}
