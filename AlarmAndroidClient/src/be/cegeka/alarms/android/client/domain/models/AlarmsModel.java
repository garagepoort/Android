package be.cegeka.alarms.android.client.domain.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.cegeka.alarms.android.client.shouldertap.events.AlarmEvent;
import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class AlarmsModel extends Tapper{

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
		AlarmEvent alarmEvent = new AlarmEvent();
		alarmEvent.setData(alarms);
		tapShoulders(alarmEvent);
	}
}
