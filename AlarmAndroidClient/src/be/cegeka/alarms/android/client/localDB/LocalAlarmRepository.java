package be.cegeka.alarms.android.client.localDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.Context;
import be.cegeka.alarms.android.client.exception.DatabaseException;
import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.*;

public class LocalAlarmRepository
{

	public List<AlarmTO> getLocalAlarms(Context context)
	{
		LocalAlarmDatabase alarmsDataSource = new LocalAlarmDatabase(context);
		alarmsDataSource.open();
		List<AlarmTO> alarms = new ArrayList<AlarmTO>();
		try
		{
			alarms = alarmsDataSource.getAllAlarmTOs();
		} catch (DatabaseException e)
		{
			throw new TechnicalException(e);
		} finally
		{
			alarmsDataSource.close();
		}
		return alarms;
	}

	public void replaceAll(Context context, List<AlarmTO> alarms)
	{
		LocalAlarmDatabase alarmsDataSource = new LocalAlarmDatabase(context);
		alarmsDataSource.open();
		try
		{
			removeAllFromLocal(alarmsDataSource);
			insertAllNew(alarmsDataSource, alarms);
			alarmsDataSource.setTransactionSuccesfull();
		} catch (DatabaseException exception)
		{
			throw new TechnicalException(exception);
		} finally
		{
			alarmsDataSource.close();
		}
	}

	private void removeAllFromLocal(LocalAlarmDatabase alarmsDataSource)
	{
		alarmsDataSource.removeAll();
	}

	private void insertAllNew(LocalAlarmDatabase alarmsDataSource,
			List<AlarmTO> alarms) throws DatabaseException
	{
		alarmsDataSource.storeAlarmTOs(alarms);
	}

	public void deleteAlarm(Context context, AlarmTO alarm)
	{
		LocalAlarmDatabase alarmDS = new LocalAlarmDatabase(context);
		alarmDS.open();
		alarmDS.deleteAlarmTO(alarm);
		alarmDS.close();
	}

	public RepeatedAlarmTO updateRepeatedAlarm(Context context,
			RepeatedAlarmTO repAlarm)
	{
		try
		{
			int unit = repAlarm.getRepeatUnit();
			int repeatQuantity = repAlarm.getRepeatQuantity();
			Calendar calRepeat = Calendar.getInstance();
			calRepeat.setTimeInMillis(repAlarm.getRepeatEnddate());
			if (calRepeat.after(Calendar.getInstance()))
			{
				Calendar newCal = Calendar.getInstance(); 
				newCal.setTimeInMillis(repAlarm.getDateInMillis());
				newCal.add(unit, repeatQuantity);
				if (newCal.before(calRepeat))
				{
					repAlarm.setDateInMillis(newCal.getTimeInMillis());
					LocalAlarmDatabase alarmDS = new LocalAlarmDatabase(context);
					alarmDS.open();
					RepeatedAlarmTO newAlarm = alarmDS.updateRepeatedAlarmTO(repAlarm);
					alarmDS.close();

					return newAlarm;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
