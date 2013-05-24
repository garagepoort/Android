package be.cegeka.alarms.android.client.shouldertap.events;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.android.ShouldrTap.AbstractEvent;
import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class AlarmEvent extends AbstractEvent<List<AlarmTO>> {

	private List<AlarmTO> alarms = new ArrayList<AlarmTO>();
	@Override
	public List<AlarmTO> getData() {
		return alarms;
	}

	@Override
	public void setData(List<AlarmTO> alarms) {
		this.alarms=alarms;
	}

}
