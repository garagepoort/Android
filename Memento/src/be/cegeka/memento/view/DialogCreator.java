package be.cegeka.memento.view;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import be.cegeka.memento.R;


public class DialogCreator
{
	public static void showErrorDialog(final String errorMessage, final Activity activity)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(activity.getString(R.string.dialog_error_title))
						.setMessage(errorMessage)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setNeutralButton(activity.getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener()
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


	public static void showEditTextDialog(final Activity activity, final DialogOKedListener<String> listener)
	{
		final EditText editText = new EditText(activity);
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(R.string.dialog_send_to_tag_title)
						.setMessage(R.string.dialog_send_to_tag_message)
						.setView(editText)
						.setPositiveButton(activity.getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								listener.okayed(editText.getText().toString());
								dialog.dismiss();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}


	public static void showEditableDropdownDialog(final Activity activity, final DialogOKedListener<String> listener, List<String> tags)
	{
		final AutoCompleteTextView autoTextView = new AutoCompleteTextView(activity);
		autoTextView.setThreshold(0);
		autoTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, tags);
		autoTextView.setAdapter(dataAdapter);
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(R.string.dialog_send_to_tag_title)
						.setMessage(R.string.dialog_send_to_tag_message)
						.setView(autoTextView)
						.setPositiveButton(activity.getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								listener.okayed(autoTextView.getText().toString());
								dialog.dismiss();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}


	public static void showOptionsDialog(final String title, final String message, final Drawable icon, final Activity activity, final DialogOKedListener<Boolean> listener)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(title)
						.setMessage(message)
						.setIcon(icon)
						.setPositiveButton(activity.getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								listener.okayed(true);
								dialog.dismiss();
							}
						})
						.setNegativeButton(activity.getString(R.string.dialog_no_button), new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								listener.okayed(false);
								dialog.dismiss();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}
}
