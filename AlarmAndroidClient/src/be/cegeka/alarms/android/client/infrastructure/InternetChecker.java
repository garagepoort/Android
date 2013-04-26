package be.cegeka.alarms.android.client.infrastructure;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetChecker implements InternetCheckerInterface {
	/**
	 * Check if the phone has an Internet connection.
	 * @return <code>true</code> if there is an Internet connection else <code>false</code>.
	 */
	@Override
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
