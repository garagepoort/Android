package be.cegeka.alarms.android.client.utilities;

import java.util.Calendar;

import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class DateChecker {

	public static boolean isDateInPast(AlarmTO alarmTO) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(alarmTO.getDateInMillis());
		return calendar.before(Calendar.getInstance());
	}
}
