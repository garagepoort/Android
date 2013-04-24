package com.cegeka.alarmmanager.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;

import com.cegeka.alarmmanager.exceptions.DatabaseException;

class LocalAlarmDatabase {

	// Database fields
	private SQLiteDatabase database;
	private AlarmSQLHelper dbHelper;
	private String[] allColumns = { 
			AlarmSQLHelper.COLUMN_ID,
			AlarmSQLHelper.COLUMN_TITLE, 
			AlarmSQLHelper.COLUMN_DESCR, 
			AlarmSQLHelper.COLUMN_DATE,
			AlarmSQLHelper.COLUMN_REPEATED,
			AlarmSQLHelper.COLUMN_REPEAT_UNIT,
			AlarmSQLHelper.COLUMN_REPEAT_UNIT_QUANTITY,
			AlarmSQLHelper.COLUMN_REPEAT_END_DATE
	};

	public LocalAlarmDatabase(Context context) {
		dbHelper = new AlarmSQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
//		database.beginTransaction();
	}

	public void close() {
		dbHelper.close();
//		database.endTransaction();
	}
	
	public void setTransactionSuccesfull()
	{
//		database.setTransactionSuccessful();
	}
	
	
	public void removeAll()
	{
		database.delete(AlarmSQLHelper.TABLE_ALARMS, "", null);
	}

	public RepeatedAlarmTO createRepeatedAlarmTO(RepeatedAlarmTO alarm) throws DatabaseException 
	{
		ContentValues values = getRepeatedAlarmTOContentValues(alarm);
		long insertId = database.insert(AlarmSQLHelper.TABLE_ALARMS, null, values);
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, AlarmSQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		RepeatedAlarmTO newAlarmTO = cursorToRepeatedAlarmTO(cursor);
		cursor.close();
		return newAlarmTO;

	}

	private ContentValues getRepeatedAlarmTOContentValues(RepeatedAlarmTO alarm) {
		ContentValues values = new ContentValues();
		values.put(AlarmSQLHelper.COLUMN_ID, alarm.getAlarmID());
		values.put(AlarmSQLHelper.COLUMN_TITLE, alarm.getTitle());
		values.put(AlarmSQLHelper.COLUMN_DESCR, alarm.getInfo());
		values.put(AlarmSQLHelper.COLUMN_DATE, alarm.getDateInMillis());
		values.put(AlarmSQLHelper.COLUMN_REPEATED, true);
		values.put(AlarmSQLHelper.COLUMN_REPEAT_UNIT, alarm.getRepeatUnit());
		values.put(AlarmSQLHelper.COLUMN_REPEAT_UNIT_QUANTITY, alarm.getRepeatQuantity());
		values.put(AlarmSQLHelper.COLUMN_REPEAT_END_DATE, alarm.getRepeatEnddate());
		return values;
	}

	public AlarmTO createAlarmTO(AlarmTO alarm) throws DatabaseException 
	{

		ContentValues values = getAlarmTOContentValues(alarm);
		long insertId = database.insert(AlarmSQLHelper.TABLE_ALARMS, null, values);
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, AlarmSQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		AlarmTO newAlarmTO = cursorToAlarmTO(cursor);
		cursor.close();
		return newAlarmTO;

	}
	
	
	public void storeAlarmTOs(List<AlarmTO> alarms) throws DatabaseException
	{
		for(AlarmTO alarm : alarms)
		{
			if(alarm instanceof RepeatedAlarmTO)
			{
				createRepeatedAlarmTO((RepeatedAlarmTO) alarm);
			}
			else
			{
				createAlarmTO(alarm);
			}
		}
	}

	private ContentValues getAlarmTOContentValues(AlarmTO alarm) {
		ContentValues values = new ContentValues();
		values.put(AlarmSQLHelper.COLUMN_ID, alarm.getAlarmID());
		values.put(AlarmSQLHelper.COLUMN_TITLE, alarm.getTitle());
		values.put(AlarmSQLHelper.COLUMN_DESCR, alarm.getInfo());
		values.put(AlarmSQLHelper.COLUMN_DATE, alarm.getDateInMillis());
		values.put(AlarmSQLHelper.COLUMN_REPEATED, false);
		values.putNull(AlarmSQLHelper.COLUMN_REPEAT_UNIT);
		values.putNull(AlarmSQLHelper.COLUMN_REPEAT_UNIT_QUANTITY);
		values.putNull(AlarmSQLHelper.COLUMN_REPEAT_END_DATE);
		return values;
	}

	public void deleteAlarmTO(AlarmTO alarm) 
	{
		long id = alarm.getAlarmID();
		database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + id, null);
	}

	public AlarmTO updateAlarmTO(AlarmTO alarm)
	{
		ContentValues values = getAlarmTOContentValues(alarm);
		String where = "id=?";
		String[] whereArgs = new String[] {String.valueOf(alarm.getAlarmID())};
		database.update(AlarmSQLHelper.TABLE_ALARMS, values, where, whereArgs);
		return (AlarmTO) getAlarmTOById(alarm.getAlarmID());
	}

	public RepeatedAlarmTO updateRepeatedAlarmTO(RepeatedAlarmTO alarm)
	{
		ContentValues values = getRepeatedAlarmTOContentValues(alarm);
		String where = "id=?";
		String[] whereArgs = new String[] {String.valueOf(alarm.getAlarmID())};
		database.update(AlarmSQLHelper.TABLE_ALARMS, values, where, whereArgs);
		return (RepeatedAlarmTO) getAlarmTOById(alarm.getAlarmID());
	}

	public ArrayList<AlarmTO> getAllAlarmTOs() throws DatabaseException 
	{
		
		ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			AlarmTO alarm;
			if(cursor.getInt(4) == 1){
				alarm = cursorToRepeatedAlarmTO(cursor);
			}
			else {
				alarm = cursorToAlarmTO(cursor);
			}
			alarms.add(alarm);
			cursor.moveToNext();
		}

		cursor.close();
		return alarms;

	}

	private RepeatedAlarmTO cursorToRepeatedAlarmTO(Cursor cursor) throws DatabaseException 
	{
		
			RepeatedAlarmTO alarm = new RepeatedAlarmTO(cursor.getInt(5),cursor.getInt(6),cursor.getLong(7),cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
			return alarm;

	}

	private AlarmTO cursorToAlarmTO(Cursor cursor) throws DatabaseException{


			AlarmTO alarm = new AlarmTO(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3));
			return alarm;
	}

	private Calendar getMillis(String millisString)
	{

		Calendar cal = Calendar.getInstance();
		long millis = Long.parseLong(millisString);
		cal.setTimeInMillis(millis);
		return cal;

	}

	public void cleanup(){

		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		List<Long> idsToDelete = new ArrayList<Long>();

		while(!cursor.isAfterLast()){

			// If repeating alarm
			if(cursor.getInt(4) == 1){
				Calendar repeatEndDate = getMillis(cursor.getString(7));
				Calendar date = getMillis(cursor.getString(3));
				int unit = cursor.getInt(5);
				int repeatUnitQuantity = cursor.getInt(6);
				if(date.before(Calendar.getInstance())){

					while(date.before(Calendar.getInstance()) && date.before(repeatEndDate)){
						date.add(unit, repeatUnitQuantity);
					}

					if(date.after(Calendar.getInstance()) && date.before(repeatEndDate)){
						ContentValues values = new ContentValues();
						values.put(AlarmSQLHelper.COLUMN_DATE, date.getTimeInMillis());
						String[] whereArgs = new String[] {String.valueOf(cursor.getLong(0))};
						database.update(AlarmSQLHelper.TABLE_ALARMS, values, AlarmSQLHelper.COLUMN_ID+"=?", whereArgs);
					}
					else {
						idsToDelete.add(cursor.getLong(0));
					}
				}
			}
			else {
				Calendar date = getMillis(cursor.getString(3));
				if(date.before(Calendar.getInstance())){
					idsToDelete.add(cursor.getLong(0));
				}
			}
			cursor.moveToNext();
		}

		for(Long id : idsToDelete){
			database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + id, null);
		}
		cursor.close();
	}


	public AlarmTO getAlarmTOById(long id){
		AlarmTO alarm = null;
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		boolean found =false;
		while(!cursor.isAfterLast() && !found){
			// If repeating alarm
			if(cursor.getInt(0) == id){
				alarm = createAlarmTOFromCursor(cursor); 
				found=true;
			}
			cursor.moveToNext();
		}
		cursor.close();
		return alarm;
	}

	public void deleteAlarmTOsByIds(ArrayList<Long> idsToDelete){
		for(Long id : idsToDelete){
			database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + id, null);
		}
	}

	public void deleteAlarmTOs(ArrayList<AlarmTO> alarms){
		for(AlarmTO alarm : alarms){
			if(alarm!=null){
				database.delete(AlarmSQLHelper.TABLE_ALARMS, AlarmSQLHelper.COLUMN_ID + " = " + alarm.getAlarmID(), null);
			}
		}
	}

	public ArrayList<AlarmTO> getAllAlarmTOsExcept(ArrayList<AlarmTO> alarms){
		ArrayList<AlarmTO> alarmsresult = new ArrayList<AlarmTO>();
		Cursor cursor = database.query(AlarmSQLHelper.TABLE_ALARMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			long id = cursor.getLong(0);
			boolean contains = false;
			for(AlarmTO a : alarms){
				if(id==a.getAlarmID()){
					contains=true;
				}
			}
			if(!contains){
				alarmsresult.add(createAlarmTOFromCursor(cursor));
			}
			cursor.moveToNext();
		}
		cursor.close();
		return alarmsresult;
	}

	private AlarmTO createAlarmTOFromCursor(Cursor cursor){
		AlarmTO alarm = null;
		if(cursor.getInt(4) == 1){
			long date = cursor.getLong(3);
			long enddate = cursor.getLong(7);
			int unit = cursor.getInt(5);
			int repeatUnitQuantity = cursor.getInt(6);
				alarm = new RepeatedAlarmTO(unit, repeatUnitQuantity, enddate, cursor.getInt(0), cursor.getString(1), cursor.getString(2), date);
		
		}else{
			long date = cursor.getLong(3);
			alarm = new AlarmTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), date);
		}
		return alarm;
	}

}
