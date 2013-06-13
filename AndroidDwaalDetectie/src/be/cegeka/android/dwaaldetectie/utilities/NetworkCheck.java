package be.cegeka.android.dwaaldetectie.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkCheck
{
	private Context context;


	/**
	 * @param context
	 *            Context of the Application.
	 */
	public NetworkCheck(Context context)
	{
		this.context = context;
	}


	/**
	 * Checks if the device is connected to the network at that moment.
	 * 
	 * @return true if the device is connected, false if the device is not
	 *         connected.
	 */
	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}
}
