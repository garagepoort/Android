package be.cegeka.alarms.android.client.sync.remoteSync;

import java.util.ArrayList;
import java.util.Observer;
import be.cegeka.alarms.android.client.exception.WebserviceException;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;

public interface RemoteDBConnectionInterface {
	
	public UserTO login(String email, String paswoord);

	public abstract void addObserver(Observer observer);

	public abstract UserTO getUser();
	
	public abstract void startAlarmsFromUser(UserTO u);
	
	public abstract void getAlarmsFromUser(String email, String paswoord)
			throws WebserviceException;

	public abstract ArrayList<AlarmTO> getAlarms();
	
	public abstract void registerSenderID(String email, String senderID);

	public abstract void unRegisterSenderID(String arg1);

}