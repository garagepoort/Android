package com.cegeka.cascadingcallstest.view;

import java.text.ParseException;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipErrorCode;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.net.sip.SipSession.Listener;

import com.cegeka.cascadingcallstest.model.Contact;

public class SIPController extends Observable {

	private SipManager mSipManager;
	private SipProfile mProfile;
	private SipRegistrationListener sipRegistrationListener;

	private boolean registered = false;
	private Context context;

	private int contactsCounter = 0;
	private List<Contact> contacts;
	//wifi
	private String domain = "172.29.162.135";
	//lan
	//private String domain = "172.31.207.72";
	// sip test network
	//private String domain = "192.168.0.197";
	
	private SipAudioCall call;

	public SIPController(Context context){
		setContext(context);
		if(mSipManager == null){
			mSipManager = SipManager.newInstance(context);
		}
		sipRegistrationListener = new RegistrationListener();
	}

	public boolean RegisterProfile(String username, String password){
		try {
			SipProfile.Builder builder = new SipProfile.Builder(username, domain);
			builder.setPassword(password);
			mProfile = builder.build();
			Intent intent = new Intent();
			intent.setAction("com.cegeka.cascadingcalls.INCOMING_CALL");
			PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, Intent.FILL_IN_DATA);
			mSipManager.open(mProfile, pendingIntent, sipRegistrationListener);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return isRegistered();
	}

	public void makeAudioCall(Contact contact, SipAudioCall.Listener listener){
		try {
			SipProfile.Builder builder = new SipProfile.Builder(contact.getNumber(), domain);
			SipProfile peerProfile = builder.build();
			call = new SipAudioCall(getContext(), mProfile);
			call = mSipManager.makeAudioCall(mProfile.getUriString(), peerProfile.getUriString(), listener, 10);
		} catch (SipException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public String getProfileName(){
		return mProfile.getUriString();
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean closeProfile(){
		try {
			if(mProfile != null && mSipManager.isOpened(mProfile.getUriString())){
				mSipManager.close(mProfile.getUriString());
				mSipManager.unregister(mProfile, sipRegistrationListener);
				setRegistered(false);
			}
		} catch (SipException e) {
			e.printStackTrace();
		}
		return isRegistered();
	}

	public void callContacts(List<Contact> contacts){

		setContacts(contacts);

		if(!contacts.isEmpty()){
			contactsCounter = 0;
			callCurrentlySelectedContact();
		}

	}

	private void callCurrentlySelectedContact() {
		makeAudioCall(getContacts().get(contactsCounter), new AudioCallListener());
	}

	public void callNext(){

		contactsCounter++;

		if(contactsCounter < getContacts().size() && getContacts().get(contactsCounter) != null){
			callCurrentlySelectedContact();
		}
		else {
			callContacts(getContacts());
		}
	}

	public void stopCalling(){
		try {
			call.endCall();
			call.close();
		} catch (SipException e) {
			e.printStackTrace();
		}
	}


	class AudioCallListener extends SipAudioCall.Listener {

		@Override
		public void onCallBusy(SipAudioCall call) {
			super.onCallBusy(call);
			System.out.println("Call ended in oncallbusy");
			callNextContact();
		}

		@Override
		public void onError(SipAudioCall call, int errorCode, String errorMessage) {
			super.onError(call, errorCode, errorMessage);
			System.out.println(errorCode + ": " + errorMessage);
			if(errorCode == SipErrorCode.SERVER_ERROR){
				callNextContact();
			}
		}

		public void callNextContact(){
			try {
				call.endCall();
			} catch (SipException e) {
				e.printStackTrace();
			}
			call.close();
			callNext();
		}
		
		@Override
		   public void onCallEstablished(SipAudioCall call) {
		      call.startAudio();
		      call.setSpeakerMode(true);
		      call.toggleMute();
		   }
	}

	class RegistrationListener implements SipRegistrationListener {

		public void onRegistering(String localProfileUri) {
			System.out.println("Registering...");
		}

		public void onRegistrationDone(final String localProfileUri, long expiryTime) {
			System.out.println("Registered");
			setRegistered(true);
			setChanged();
			SIPController.this.notifyObservers(true);
		}

		public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
			setRegistered(false);
			setChanged();
			notifyObservers(false);
		}

	}

}
