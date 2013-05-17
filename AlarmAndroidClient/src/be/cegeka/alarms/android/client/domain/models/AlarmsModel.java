package be.cegeka.alarms.android.client.domain.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class AlarmsModel extends Observable{

	private List<AlarmTO> alarms;
	private static final AlarmsModel instance = new AlarmsModel();
	
	private AlarmsModel(){
		this.alarms = new ArrayList<AlarmTO>();
	}
	
	public static AlarmsModel getInstance(){
		return instance;
	}

	public List<AlarmTO> getAlarms() {
		return Collections.unmodifiableList(alarms);
	}

	public void setAlarms(List<AlarmTO> alarms) {
		this.alarms = alarms;
		setChanged();
		notifyObservers(alarms);
	}
	
	
	
}
