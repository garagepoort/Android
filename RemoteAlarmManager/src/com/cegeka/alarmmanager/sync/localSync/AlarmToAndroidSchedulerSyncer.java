package com.cegeka.alarmmanager.sync.localSync;

import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import be.cegeka.android.alarms.transferobjects.AlarmTO;

import com.cegeka.alarmmanager.utilities.DateChecker;
import com.cegeka.alarmmanager.view.AlarmReceiverActivity;

public class AlarmToAndroidSchedulerSyncer {

	/**
	 * schedule an {@link AlarmTO} in the {@link AlarmManager}.
	 * 
	 * @param context
	 *            The context
	 * @param alarm
	 *            The {@link AlarmTO} to be scheduled.
	 */
	public static void scheduleAlarms(Context context, List<AlarmTO> alarmen) {
		for (AlarmTO alarm : alarmen) {
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
	public static void cancelAlarms(Context context, List<AlarmTO> alarmen) {
		for (AlarmTO a : alarmen) {
			cancelAlarm(context, a);
		}
	}

	private static void cancelAlarm(Context context, AlarmTO alarm) {
		PendingIntent pintent = createPendingIntentFor(context, alarm);
		boolean wasScheduledBefore = pintent != null;
		if (wasScheduledBefore) {
			alarmManager(context).cancel(pintent);
		}
	}


	public static void scheduleAlarm(Context context, AlarmTO alarm) {
		if (DateChecker.isDateInPast(alarm)) { 
			return;
		}
		
		PendingIntent pendingIntent = createPendingIntentFor(context, alarm);
		alarmManager(context).set(
				AlarmManager.RTC_WAKEUP, 
				alarm.getDateInMillis(),
				pendingIntent);
	}
	

	private static PendingIntent createPendingIntentFor(Context context, AlarmTO alarm) {
		Intent intent = new Intent(context, AlarmReceiverActivity.class);
		intent.putExtra("AlarmTO", alarm);
		return PendingIntent.getActivity(
				context,
				(int) alarm.getAlarmID(), 
				intent, 
				PendingIntent.FLAG_CANCEL_CURRENT);
	}

	private static AlarmManager alarmManager(Context context) {
		return (AlarmManager) context
				.getSystemService(Activity.ALARM_SERVICE);
	}


}
