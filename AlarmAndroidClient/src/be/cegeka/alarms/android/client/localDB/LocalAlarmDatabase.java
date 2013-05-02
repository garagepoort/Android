package be.cegeka.alarms.android.client.localDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import be.cegeka.alarms.android.client.exception.DatabaseException;
import be.cegeka.alarms.android.client.utilities.AlarmUtilities;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;

class LocalAlarmDatabase {

	// Database fields
	private SQLiteDatabase database;
	private AlarmSQLHelper dbHelper;
	private String[] allColumns = { AlarmSQLHelper.COLUMN_ID, AlarmSQLHelper.COLUMN_TITLE, AlarmSQLHelper.COLUMN_DESCR, AlarmSQLHelper.COLUMN_DATE, AlarmSQLHelper.COLUMN_REPEATED, AlarmSQLHelper.COLUMN_REPEAT_UNIT, AlarmSQLHelper.COLUMN_REPEAT_UNIT_QUANTITY, AlarmSQLHelper.COLUMN_REPEAT_END_DATE };

	public LocalAlarmDatabase(Context context) {
		dbHelper = new AlarmSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void removeAll() {
		database.delete(AlarmSQLHelper.TABLE_ALARMS, "", null);
	}

	// 1. Insert alarm ×
	// 2. Delete alarm ×
	// 3. update alarm ×
	// 4. get alarm ×

	private ContentValues getAlarmTOContentValues(AlarmTO alarm) {
		// Put in standard alarm values
		ContentValues values = new ContentValues();
		values.put(AlarmSQLHelper.COLUMN_ID, alarm.getAlarmID());
		values.put(AlarmSQLHelper.COLUMN_TITLE, alarm.getTitle());
		values.put(AlarmSQLHelper.COLUMN_DESCR, alarm.getInfo());
		values.put(AlarmSQLHelper.COLUMN_DATE, alarm.getDateInMillis());
		values.put(AlarmSQLHelper.COLUMN_REPEATED, false);
		// Put in repeated values if needed
		if (alarm instanceof RepeatedAlarmTO) {
			RepeatedAlarmTO rAlarm = (RepeatedAlarmTO) alarm;
			values.put(AlarmSQLHelper.COLUMN_REPEATED, true);
			values.put(AlarmSQLHelper.COLUMN_REPEAT_UNIT, rAlarm.getRepeatUnit());
			values.put(AlarmSQLHelper.COLUMN_REPEAT_UNIT_QUANTITY, rAlarm.getRepeatQuantity());
			values.put(AlarmSQLHelper.COLUMN_REPEAT_END_DATE, rAlarm.getRepeatEnddate());
		}
		return values;
	}

	public AlarmTO createAlarmTO(AlarmTO alarm) throws DatabaseException {

		ContentValues values = getAlarmTOContentValues(alarm);
		long insertId = database.insert(AlarmSQLHelper.TABLE_ALARMS, null, values);
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, AlarmSQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		AlarmTO newAlarmTO = cursorToAlarmTO(cursor);
		cursor.close();
		return newAlarmTO;

	}

	public void storeAlarmTOs(List<AlarmTO> alarms) throws DatabaseException {
		for (AlarmTO alarm : alarms) {
			createAlarmTO(alarm);
		}
	}

	public void deleteAlarmTO(AlarmTO alarm) {
		long id = alarm.getAlarmID();
		database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + id, null);
	}

	public AlarmTO updateAlarmTO(AlarmTO alarm) {
		ContentValues values = getAlarmTOContentValues(alarm);
		String where = "id=?";
		String[] whereArgs = new String[] { String.valueOf(alarm.getAlarmID()) };
		database.update(AlarmSQLHelper.TABLE_ALARMS, values, where, whereArgs);
		return (AlarmTO) getAlarmTOById(alarm.getAlarmID());
	}

	public ArrayList<AlarmTO> getAllAlarmTOs() throws DatabaseException {

		ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			AlarmTO alarm = cursorToAlarmTO(cursor);
			alarms.add(alarm);
			cursor.moveToNext();
		}

		cursor.close();
		return alarms;

	}

	private AlarmTO cursorToAlarmTO(Cursor cursor) throws DatabaseException {

		AlarmTO alarm = new AlarmTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3));
		if (cursor.getInt(4) > 0) {
			RepeatedAlarmTO rAlarm = new RepeatedAlarmTO(alarm);
			rAlarm.setRepeatUnit(cursor.getInt(5));
			rAlarm.setRepeatQuantity(cursor.getInt(6));
			rAlarm.setRepeatEnddate(cursor.getLong(7));
			return rAlarm;
		}
		return alarm;
	}

	private Calendar getMillis(String millisString) {

		Calendar cal = Calendar.getInstance();
		long millis = Long.parseLong(millisString);
		cal.setTimeInMillis(millis);
		return cal;

	}

	public void cleanup() throws DatabaseException {

		List<AlarmTO> alarms = new ArrayList<AlarmTO>(getAllAlarmTOs());

		for (AlarmTO alarm : alarms) {

			Calendar eventDate = Calendar.getInstance();
			eventDate.setTimeInMillis(alarm.getDateInMillis());

			if (eventDate.before(Calendar.getInstance())) {

				if (alarm instanceof RepeatedAlarmTO) {

					tryFuturizationOfRepeatedAlarmTO(alarm);

				}
				else {
					deleteAlarmTO(alarm);
				}
			}
		}
	}

	public void tryFuturizationOfRepeatedAlarmTO(AlarmTO alarm) {
		
		AlarmUtilities alarmUtilities = new AlarmUtilities();
		
		RepeatedAlarmTO rAlarm = (RepeatedAlarmTO) alarm;
		RepeatedAlarmTO futurizedAlarm = alarmUtilities.futurizeRepeatedAlarmEventDate(rAlarm);

		if (futurizedAlarm == null) {
			deleteAlarmTO(futurizedAlarm);
		}
		else {
			updateAlarmTO(futurizedAlarm);
		}
	}

	public AlarmTO getAlarmTOById(long id) {
		AlarmTO alarm = null;
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		boolean found = false;
		while (!cursor.isAfterLast() && !found) {
			// If repeating alarm
			if (cursor.getInt(0) == id) {
				alarm = createAlarmTOFromCursor(cursor);
				found = true;
			}
			cursor.moveToNext();
		}
		cursor.close();
		return alarm;
	}

	public void deleteAlarmTOsByIds(ArrayList<Long> idsToDelete) {
		for (Long id : idsToDelete) {
			database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + id, null);
		}
	}

	public void deleteAlarmTOs(ArrayList<AlarmTO> alarms) {
		for (AlarmTO alarm : alarms) {
			if (alarm != null) {
				database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + alarm.getAlarmID(), null);
			}
		}
	}

	public ArrayList<AlarmTO> getAllAlarmTOsExcept(ArrayList<AlarmTO> alarms) {
		ArrayList<AlarmTO> alarmsresult = new ArrayList<AlarmTO>();
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long id = cursor.getLong(0);
			boolean contains = false;
			for (AlarmTO a : alarms) {
				if (id == a.getAlarmID()) {
					contains = true;
				}
			}
			if (!contains) {
				alarmsresult.add(createAlarmTOFromCursor(cursor));
			}
			cursor.moveToNext();
		}
		cursor.close();
		return alarmsresult;
	}

	private AlarmTO createAlarmTOFromCursor(Cursor cursor) {
		AlarmTO alarm = null;
		if (cursor.getInt(4) == 1) {
			long date = cursor.getLong(3);
			long enddate = cursor.getLong(7);
			int unit = cursor.getInt(5);
			int repeatUnitQuantity = cursor.getInt(6);
			alarm = new RepeatedAlarmTO(unit, repeatUnitQuantity, enddate, cursor.getInt(0), cursor.getString(1), cursor.getString(2), date);

		}
		else {
			long date = cursor.getLong(3);
			alarm = new AlarmTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), date);
		}
		return alarm;
	}

}
