package be.cegeka.android.dwaaldetectie.dialogs;

import be.cegeka.android.dwaaldetectie.R;
import be.cegeka.android.dwaaldetectie.view.MainActivity;
import be.cegeka.android.dwaaldetectie.view.listeners.DialogDismissListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


/**
 * Handles the error code RESULT_NO_ADDRESS
 */
public class HandleProblemStartNoAddress implements HandleProblemStartInterface
{

	@Override
	public void handle(Context context)
	{
		final Context context2 = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.error_error))
				.setMessage(context.getString(R.string.error_no_address_saved))
				.setPositiveButton(context.getString(R.string.error_button_positive), new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						
						MainActivity mainActivity = (MainActivity) context2;
						mainActivity.handleShowMap(null);
					}
				})
				.setNegativeButton(context.getString(R.string.button_cancel), new DialogDismissListener());

		builder.create().show();
	}

}
