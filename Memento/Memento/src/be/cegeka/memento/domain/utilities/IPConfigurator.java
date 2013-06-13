package be.cegeka.memento.domain.utilities;

import static be.cegeka.memento.domain.utilities.SharedPrefsManager.saveSharedPreference;
import static be.cegeka.memento.view.DialogCreator.showEditTextDialog;
import static be.cegeka.memento.view.DialogCreator.showErrorDialog;
import android.app.Activity;
import android.view.MenuItem;
import be.cegeka.memento.R;
import be.cegeka.memento.view.DialogOKedListener;


public class IPConfigurator
{
	public static void configureIPAddress(final Activity activity, MenuItem item)
	{
		showEditTextDialog(activity, activity.getString(R.string.dialog_configure_IP_title), activity.getString(R.string.dialog_configure_IP_message), new DialogOKedListener<String>()
		{
			@Override
			public void okayed(String input)
			{
				String IPADDRESS_PATTERN =
						"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

				if (input.matches(IPADDRESS_PATTERN))
				{
					saveSharedPreference(activity, "URL", "http://" + input + ":8080/MementoServer/MementoWebService");
				}
				else
				{
					showErrorDialog("geen goed ip", activity);
				}
			}
		});
	}
}
