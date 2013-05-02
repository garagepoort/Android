<<<<<<< HEAD
package be.cegeka.alarms.android.client.utilities;

import java.util.Calendar;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;

public class AlarmUtilities {

	public static boolean isDateInPast(AlarmTO alarmTO) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(alarmTO.getDateInMillis());
		return calendar.before(Calendar.getInstance());
	}

	public RepeatedAlarmTO futurizeRepeatedAlarmEventDate(RepeatedAlarmTO rAlarm) {

		Calendar endDate = Calendar.getInstance();
		Calendar eventDate = Calendar.getInstance();

		endDate.setTimeInMillis(rAlarm.getRepeatEnddate());
		eventDate.setTimeInMillis(rAlarm.getDateInMillis());

		if (endDate.after(Calendar.getInstance())) {
			while (eventDate.before(endDate) && eventDate.before(Calendar.getInstance())) {
				eventDate.add(rAlarm.getRepeatUnit(), rAlarm.getRepeatQuantity());
				rAlarm.setDateInMillis(eventDate.getTimeInMillis());
			}
			if (eventDate.before(endDate) && eventDate.before(Calendar.getInstance())) {
				return rAlarm;
			}
		}
		return null;
	}

}
=======
package be.cegeka.alarms.android.client.utilities;

import java.util.Calendar;

import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.exception.UtilityException;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.RepeatedAlarmTO;

public class AlarmUtilities {

	public static boolean isDateInPast(AlarmTO alarmTO) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(alarmTO.getDateInMillis());
		return calendar.before(Calendar.getInstance());
	}

	public RepeatedAlarmTO futurizeRepeatedAlarmEventDate(RepeatedAlarmTO rAlarm) throws UtilityException {
		
		if(rAlarm == null){
			throw new UtilityException("The alarm can't be null");
		}
		
		Calendar endDate = Calendar.getInstance();
		Calendar eventDate = Calendar.getInstance();

		endDate.setTimeInMillis(rAlarm.getRepeatEnddate());
		eventDate.setTimeInMillis(rAlarm.getDateInMillis());

		if (endDate.after(Calendar.getInstance())) {
			while (eventDate.before(endDate) && eventDate.before(Calendar.getInstance())) {
				eventDate.add(rAlarm.getRepeatUnit(), rAlarm.getRepeatQuantity());
				rAlarm.setDateInMillis(eventDate.getTimeInMillis());
			}
			if (eventDate.before(endDate) && eventDate.after(Calendar.getInstance())) {
				return rAlarm;
			}
		}
		return null;
	}

}
>>>>>>> 6fe7d35ce9bc8068a3ec9951943d0e9ffebf5353
