package com.cegeka.alarmmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AlarmSQLHelper extends SQLiteOpenHelper {

	// Table info strings
	public static final String TABLE_ALARMS = "alarms";
	// Column names
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCR = "description";
	public static final String COLUMN_DATE = "eventDate";
	public static final String COLUMN_REPEATED = "repeated";
	public static final String COLUMN_REPEAT_UNIT = "repeatunit";
	public static final String COLUMN_REPEAT_UNIT_QUANTITY = "repeatunitquantity";
	public static final String COLUMN_REPEAT_END_DATE = "repeatenddate";

	private static final String DATABASE_NAME = "alarms.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_ALARMS + "(" 
			+ COLUMN_ID	+ " integer primary key, " 
			+ COLUMN_TITLE + " text not null, " 
			+ COLUMN_DESCR + " text not null, "
			+ COLUMN_DATE + " text,"
			+ COLUMN_REPEATED + " boolean not null, "
			+ COLUMN_REPEAT_UNIT + " integer, "
			+ COLUMN_REPEAT_UNIT_QUANTITY + " integer, "
			+ COLUMN_REPEAT_END_DATE + " text);";

	public AlarmSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(AlarmSQLHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
		onCreate(db);
	}

}
