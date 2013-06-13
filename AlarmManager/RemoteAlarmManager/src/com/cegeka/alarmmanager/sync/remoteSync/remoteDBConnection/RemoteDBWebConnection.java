package com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;

import com.cegeka.alarmmanager.exceptions.WebserviceException;

public class RemoteDBWebConnection extends Observable implements RemoteDBConnectionInterface, Observer{

	private RemoteDBSoapRequest r;
	/**
	 * @see com.cegeka.alarmmanager.RemoteDBConnectionInterface.ConnectionInterface#startUserLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public void startUserLogin(String email, String paswoord) {
		r = new RemoteDBSoapRequest();
		r.addObserver(this);
		r.execute(RemoteDBSoapRequest.GET_USER,email, paswoord);
	}
	
	/**
	 * @see com.cegeka.alarmmanager.RemoteDBConnectionInterface.ConnectionInterface#getUser()
	 */
	@Override
	public UserTO getUser(){
		return r.getUser();
	}
	

	/**
	 * @see com.cegeka.alarmmanager.RemoteDBConnectionInterface.ConnectionInterface#startAlarmsFromUser(com.cegeka.alarmmanager.model.User)
	 */
	@Override
	public void startAlarmsFromUser(UserTO u){
		r = new RemoteDBSoapRequest();
		r.addObserver(this);
		r.execute(RemoteDBSoapRequest.GET_ALARMS_FROM_USER, u.getEmail(), u.getPaswoord());
	}

	/** 
	 * @see com.cegeka.alarmmanager.RemoteDBConnectionInterface.ConnectionInterface#getAlarmsFromUser(java.lang.String, java.lang.String)
	 */
	@Override
	public void getAlarmsFromUser(String email, String paswoord) throws WebserviceException{
		r = new RemoteDBSoapRequest();
		r.execute(RemoteDBSoapRequest.GET_ALARMS_FROM_USER, email, paswoord);
	}

	/**
	 * @see com.cegeka.alarmmanager.RemoteDBConnectionInterface.ConnectionInterface#getAlarms()
	 */
	@Override
	public ArrayList<AlarmTO> getAlarms(){
		return r.getAlarms();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
		notifyObservers();
	}

	@Override
	public void registerSenderID(String email, String senderID) {
		r = new RemoteDBSoapRequest();
		r.addObserver(this);
		r.execute(RemoteDBSoapRequest.REGISTER_SENDERID, email, senderID);
	}

	@Override
	public void unRegisterSenderID(String senderID) {
		r = new RemoteDBSoapRequest();
		r.addObserver(this);
		r.execute(RemoteDBSoapRequest.UNREGISTER_SENDERID, senderID);
		
	}
}
