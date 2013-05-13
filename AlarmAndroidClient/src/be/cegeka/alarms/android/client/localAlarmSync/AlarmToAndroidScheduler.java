package be.cegeka.alarms.android.client.localAlarmSync;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import be.cegeka.alarms.android.client.activities.AlarmReceiverActivity;
import be.cegeka.alarms.android.client.utilities.AlarmUtilities;
import be.cegeka.android.alarms.transferobjects.AlarmTO;


public class AlarmToAndroidScheduler
{

	/**
	 * schedule an {@link AlarmTO} in the {@link AlarmManager}.
	 * 
	 * @param context
	 *            The context
	 * @param alarm
	 *            The {@link AlarmTO} to be scheduled.
	 */
	public static void scheduleAlarms(Context context, List<AlarmTO> alarmen)
	{
		for (AlarmTO alarm : alarmen)
		{
			scheduleAlarm(context, alarm);
		}

	}


	/**
	 * Cancel the given Alarms from the {@link AlarmManager}.
	 * 
	 * @param context
	 *            The context.
	 * @param alarmen
	 *            The alarms to cancel.
	 */
	public static void cancelAlarms(Context context, List<AlarmTO> alarmen)
	{
		for (AlarmTO a : alarmen)
		{
			cancelAlarm(context, a);
		}
	}


	private static void cancelAlarm(Context context, AlarmTO alarm)
	{
		PendingIntent pintent = createPendingIntentFor(context, alarm);
		boolean wasScheduledBefore = pintent != null;
		if (wasScheduledBefore)
		{
			alarmManager(context).cancel(pintent);
		}
	}


	public static void scheduleAlarm(Context context, AlarmTO alarm)
	{
		if (AlarmUtilities.isDateInPast(alarm))
		{
			return;
		}

		PendingIntent pendingIntent = createPendingIntentFor(context, alarm);
		alarmManager(context).set(AlarmManager.RTC_WAKEUP, alarm.getDateInMillis(), pendingIntent);
		
		System.out.println(alarm.getDateInMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(alarm.getDateInMillis());
		System.out.println(calendar.getTime().toString());
	}


	private static PendingIntent createPendingIntentFor(Context context, AlarmTO alarm)
	{
		Intent intent = new Intent(context, AlarmReceiverActivity.class);
		intent.putExtra("Alarm", alarm);
		return PendingIntent.getActivity(context, (int) alarm.getAlarmID(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
	}


	private static AlarmManager alarmManager(Context context)
	{
		return (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
	}
	
//	public static void scheduleTestAlarm(Context ctx){
//		 // get a Calendar object with current time
//		 Calendar cal = Calendar.getInstance();
//		 // add 5 minutes to the calendar object
//		 cal.add(Calendar.SECOND, 2);
//		 Intent intent = new Intent(ctx, AlarmReceiver.class);
//		 intent.putExtra("alarm_message", "O'Doyle Rules!");
//		 // In reality, you would want to have a static variable for the request code instead of 192837
//		 PendingIntent sender = PendingIntent.getBroadcast(ctx, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		 
//		 // Get the AlarmManager service
//		 AlarmManager am = (AlarmManager) ctx.getSystemService(Activity.ALARM_SERVICE);
//		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
//	}

}
