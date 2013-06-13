package be.cegeka.android.splitit.view;

import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.EditText;
import be.cegeka.android.splitit.R;
import be.cegeka.android.splitit.domain.Person;
import facade.Facade;


public class DialogCreator
{
	private static String person1Name;
	private static String person2Name;


	public static void showSetPeopleDialog(final Activity activity)
	{
		final EditText person1 = new EditText(activity);
		final EditText person2 = new EditText(activity);

		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(activity.getString(R.string.settings_title))
						.setMessage(activity.getString(R.string.settings_message_person_1))
						.setView(person1)
						.setPositiveButton(activity.getString(R.string.settings_next_button), new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								person1Name = person1.getText().toString();
								dialog.dismiss();

								activity.runOnUiThread(new Runnable()
								{
									@Override
									public void run()
									{
										AlertDialog.Builder builder = new AlertDialog.Builder(activity)
												.setTitle(activity.getString(R.string.settings_title))
												.setMessage(activity.getString(R.string.settings_message_person_2))
												.setView(person2)
												.setPositiveButton(activity.getString(R.string.settings_save_button), new DialogInterface.OnClickListener()
												{
													public void onClick(DialogInterface dialog, int id)
													{
														person2Name = person2.getText().toString();
														Facade facade = new Facade(activity);
														try
														{
															Person person1 = new Person(person1Name);
															Person person2 = new Person(person2Name);
															facade.savePeople(person1, person2);
														}
														catch (IOException e)
														{
															e.printStackTrace();
															showErrorDialog(activity);
														}
														dialog.dismiss();
													}
												})
												.setNegativeButton(activity.getString(R.string.settings_cancel_button), new DialogInterface.OnClickListener()
												{
													public void onClick(DialogInterface dialog, int id)
													{
														dialog.dismiss();
													}
												});
										Dialog dialog = builder.create();
										dialog.show();
										dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
									}
								});
							}
						})
						.setNegativeButton(activity.getString(R.string.settings_cancel_button), new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								dialog.dismiss();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}


	public static void showErrorDialog(final Activity activity)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(activity.getString(R.string.error_title))
						.setMessage(activity.getString(R.string.error_message))
						.setIcon(activity.getResources().getDrawable(android.R.drawable.alert_dark_frame))
						.setNeutralButton(activity.getString(R.string.error_button), new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.dismiss();
							}
						});
				Dialog dialog = builder.create();
				dialog.show();
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}


	public static void showConfirmationDialog(final Activity activity)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(activity)
						.setTitle(activity.getString(R.string.confirmation_title))
						.setMessage(activity.getString(R.string.confirmation_message))
						.setPositiveButton(activity.getString(R.string.confirmation_confirm_button), new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Facade facade = new Facade(activity);
								try
								{
									facade.resetAmounts();
								}
								catch (IOException e)
								{
									e.printStackTrace();
									showErrorDialog(activity);
								}
								dialog.dismiss();
							}
						})
						.setNegativeButton(activity.getString(R.string.confirmation_cancel_button), new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
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
