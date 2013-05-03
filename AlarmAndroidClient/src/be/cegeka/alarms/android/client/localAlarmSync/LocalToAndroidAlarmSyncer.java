package be.cegeka.alarms.android.client.localAlarmSync;

import android.content.Context;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;


public class LocalToAndroidAlarmSyncer
{
	private LocalAlarmRepository localAlarmRepository;
	private Context context;
	

	public LocalToAndroidAlarmSyncer(Context context)
	{
		this.context = context;
		this.localAlarmRepository = new LocalAlarmRepository(context);
	}


	public void unscheduleAllAlarms()
	{
		AlarmToAndroidSchedulerSyncer.cancelAlarms(context, localAlarmRepository.getLocalAlarms());
	}


	public void scheduleAllAlarms()
	{
		AlarmToAndroidSchedulerSyncer.scheduleAlarms(context, localAlarmRepository.getLocalAlarms());
	}
}
