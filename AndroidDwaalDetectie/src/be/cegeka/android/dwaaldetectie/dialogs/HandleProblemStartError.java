package be.cegeka.android.dwaaldetectie.dialogs;

import be.cegeka.android.dwaaldetectie.R;
import be.cegeka.android.dwaaldetectie.view.listeners.DialogDismissListener;
import android.app.AlertDialog;
import android.content.Context;


/**
 * Handles the error code RESULT_ERROR
 */
public class HandleProblemStartError implements HandleProblemStartInterface
{

	@Override
	public void handle(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.error_error))
				.setMessage(context.getString(R.string.error_unable_to_start))
				.setNegativeButton(context.getString(R.string.button_cancel), new DialogDismissListener());

		builder.create().show();
	}

}
