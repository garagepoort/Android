package be.cegeka.alarms.android.client.localDB;

import java.util.List;
import android.content.Context;
import be.cegeka.alarms.android.client.exception.DatabaseException;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;

public class LocalAlarmRepository {

	private LocalAlarmDatabase alarmDataSource;

	public LocalAlarmRepository(Context context) {
		alarmDataSource = new LocalAlarmDatabase(context);
	}

	public List<AlarmTO> getLocalAlarms() {

		alarmDataSource.open();
		
		try {
			return alarmDataSource.getAllAlarmTOs();
		}
		catch (DatabaseException e) {
			throw new TechnicalException(e);
		}
		finally {
			alarmDataSource.close();
		}
	}

	public void replaceAll(List<AlarmTO> alarms) {
		
		alarmDataSource.open();
		try {
			removeAllFromLocal();
			insertAllNew(alarms);
		}
		catch (DatabaseException exception) {
			throw new TechnicalException(exception);
		}
		finally {
			alarmDataSource.close();
		}
	}

	private void removeAllFromLocal() {
		alarmDataSource.removeAll();
	}

	private void insertAllNew(List<AlarmTO> alarms) throws DatabaseException {
		alarmDataSource.storeAlarmTOs(alarms);
	}

	public void deleteAlarm(AlarmTO alarm) throws DatabaseException {
		alarmDataSource.open();
		alarmDataSource.deleteAlarmTO(alarm);
		alarmDataSource.close();
	}

	public RepeatedAlarmTO updateRepeatedAlarm(RepeatedAlarmTO rAlarm) throws DatabaseException {
		
		alarmDataSource.open();
		alarmDataSource.tryFuturizationOfRepeatedAlarmTO(rAlarm);
		rAlarm = (RepeatedAlarmTO) alarmDataSource.getAlarmTOById(rAlarm.getAlarmID());
		alarmDataSource.close();
		return rAlarm;
	}
}
