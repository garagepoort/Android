package com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection;

import java.util.ArrayList;
import java.util.Observer;

import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;

import com.cegeka.alarmmanager.exceptions.WebserviceException;

public interface RemoteDBConnectionInterface {

	
	public abstract void startUserLogin(String email, String paswoord);

	public abstract void addObserver(Observer observer);

	public abstract UserTO getUser();
	
	public abstract void startAlarmsFromUser(UserTO u);
	
	public abstract void getAlarmsFromUser(String email, String paswoord)
			throws WebserviceException;

	public abstract ArrayList<AlarmTO> getAlarms();
	
	public abstract void registerSenderID(String email, String senderID);

	public abstract void unRegisterSenderID(String arg1);

}