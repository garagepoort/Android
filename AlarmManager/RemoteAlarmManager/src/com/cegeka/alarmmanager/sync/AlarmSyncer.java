package com.cegeka.alarmmanager.sync;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;

import com.cegeka.alarmmanager.sync.localSync.LocalToAndroidAlarmSyncer;
import com.cegeka.alarmmanager.sync.remoteSync.RemoteToLocalAlarmSyncer;


public class AlarmSyncer extends Observable implements Observer{
	
	private static final AlarmSyncer instance = new AlarmSyncer();
	private LocalToAndroidAlarmSyncer localToAndroidAlarmSyncer = new LocalToAndroidAlarmSyncer();
	private Context context;
	private AlarmSyncer(){}
	
	public static AlarmSyncer getInstance(){
		return instance;
	}
	
	public void syncAllAlarms(Context context)
	{
		this.context=context;
		RemoteToLocalAlarmSyncer remoteToLocalAlarmSyncer = new RemoteToLocalAlarmSyncer();
		
		remoteToLocalAlarmSyncer.addObserver(this);
		
		localToAndroidAlarmSyncer.unscheduleAllAlarms(context);
		remoteToLocalAlarmSyncer.sync(context);
		
	}

	@Override
	public void update(Observable observable, Object data) {
		localToAndroidAlarmSyncer.scheduleAllAlarms(context);
		setChanged();
		notifyObservers();
	}
}
