package be.cegeka.alarms.android.client.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import be.cegeka.alarms.android.client.R;

public class DialogCreator
{

	public static void buildAndShowErrorDialog(final String errorMessage, final Activity activity)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage(errorMessage).setPositiveButton(activity.getString(R.string.button_error_message_accept), new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
	}
}
