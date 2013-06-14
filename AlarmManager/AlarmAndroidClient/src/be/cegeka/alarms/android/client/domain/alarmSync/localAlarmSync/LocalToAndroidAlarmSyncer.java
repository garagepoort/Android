package be.cegeka.alarms.android.client.domain.alarmSync.localAlarmSync;

import android.content.Context;
import be.cegeka.alarms.android.client.domain.controllers.LocalAlarmRepository;
import be.cegeka.alarms.android.client.domain.exception.TechnicalException;


public class LocalToAndroidAlarmSyncer
{
	private LocalAlarmRepository localAlarmRepository;
	private Context context;
	

	public LocalToAndroidAlarmSyncer(Context context)
	{
		this.context = context;
		this.localAlarmRepository = new LocalAlarmRepository(context);
	}


	public void unscheduleAllAlarms() throws TechnicalException
	{
		AlarmToAndroidScheduler.cancelAlarms(context, localAlarmRepository.getLocalAlarms());
	}


	public void scheduleAllAlarms() throws TechnicalException
	{
		AlarmToAndroidScheduler.scheduleAlarms(context, localAlarmRepository.getLocalAlarms());
	}
}
