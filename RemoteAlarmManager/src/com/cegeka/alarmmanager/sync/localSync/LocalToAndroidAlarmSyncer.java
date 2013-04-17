package com.cegeka.alarmmanager.sync.localSync;

import android.content.Context;

import com.cegeka.alarmmanager.db.LocalAlarmRepository;

public class LocalToAndroidAlarmSyncer {

	public void unscheduleAllAlarms(Context context){
		AlarmToAndroidSchedulerSyncer.cancelAlarms(context, LocalAlarmRepository.getLocalAlarms(context));
	}

	public void scheduleAllAlarms(Context context){
			AlarmToAndroidSchedulerSyncer.scheduleAlarms(context, LocalAlarmRepository.getLocalAlarms(context));
	}

}
