//package be.cegeka.alarms.android.client.test;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import be.cegeka.alarms.android.client.exception.DatabaseException;
//import be.cegeka.alarms.android.client.localDB.LocalAlarmDatabase;
//import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
//import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.RepeatedAlarmTO;
//import android.test.AndroidTestCase;
//import android.test.RenamingDelegatingContext;
//
//public class LocalDatabaseTest extends AndroidTestCase {
//
//	private LocalAlarmDatabase db;
//	private AlarmTO alarm;
//	private RepeatedAlarmTO rAlarm;
//
//	public void setUp() {
//		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
//		db = new LocalAlarmDatabase(context);
//		alarm = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		rAlarm = new RepeatedAlarmTO(alarm);
//		rAlarm.setRepeatUnit(Calendar.MINUTE);
//		rAlarm.setRepeatQuantity(2);
//		rAlarm.setRepeatEnddate(Calendar.getInstance().getTimeInMillis());
//		db.open();
//	}
//
//	public void testRemoveAll() throws DatabaseException {
//		AlarmTO alarm1 = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm2 = new AlarmTO(6, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm3 = new AlarmTO(7, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//
//		db.createAlarmTO(alarm1);
//		db.createAlarmTO(alarm2);
//		db.createAlarmTO(alarm3);
//
//		assertTrue(db.getAllAlarmTOs().size() == 3);
//
//		db.removeAll();
//
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//	}
//
//	public void testCreateAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		AlarmTO alarmResult = db.createAlarmTO(alarm);
//		assertTrue(db.getAllAlarmTOs().size() == 1);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//	}
//
//	public void testCreateAlarmTOExceptionWithNullParameter() {
//		try {
//			db.createAlarmTO(null);
//			fail("Expected a DatabaseException.");
//		}
//		catch (DatabaseException ex) {
//		}
//	}
//
//	public void testCreateAlarmTOWithRepeatedAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		RepeatedAlarmTO alarmResult = (RepeatedAlarmTO) db.createAlarmTO(rAlarm);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//	}
//
//	public void testSaveListOfAlarmTOs() throws DatabaseException {
//		AlarmTO alarm1 = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm2 = new AlarmTO(6, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm3 = new AlarmTO(7, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//
//		List<AlarmTO> alarms = new ArrayList<AlarmTO>();
//		alarms.add(alarm1);
//		alarms.add(alarm2);
//		alarms.add(alarm3);
//
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//
//		db.storeAlarmTOs(alarms);
//
//		assertTrue(db.getAllAlarmTOs().size() == 3);
//		assertTrue(db.getAllAlarmTOs().contains(alarm1));
//		assertTrue(db.getAllAlarmTOs().contains(alarm2));
//		assertTrue(db.getAllAlarmTOs().contains(alarm3));
//	}
//
//	public void testSaveListOfAlarmTOsExceptionWithNullParameter() {
//		try {
//			db.storeAlarmTOs(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void testDeleteAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		AlarmTO alarmResult = db.createAlarmTO(alarm);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//		db.deleteAlarmTO(alarmResult);
//		assertFalse(db.getAllAlarmTOs().contains(alarmResult));
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//	}
//
//	public void testDeleteAlarmTOExceptionWithNullParameter() {
//		try {
//			db.deleteAlarmTO(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void testDeleteAlarmTOWithRepeatedAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		AlarmTO alarmResult = db.createAlarmTO(rAlarm);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//		db.deleteAlarmTO(alarmResult);
//		assertFalse(db.getAllAlarmTOs().contains(alarmResult));
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//	}
//
//	public void testUpdateAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		AlarmTO alarmResult = db.createAlarmTO(alarm);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//		alarmResult.setInfo("updatedInfo");
//		AlarmTO updatedAlarm = db.updateAlarmTO(alarmResult);
//		AlarmTO updatedAlarmResult = db.getAlarmTOById(updatedAlarm.getAlarmID());
//		assertTrue(db.getAllAlarmTOs().contains(updatedAlarm));
//		assertEquals(updatedAlarm, updatedAlarmResult);
//		assertEquals(updatedAlarmResult.getInfo(), "updatedInfo");
//	}
//
//	public void testUpdateAlarmTOExceptionWithNullParameter() {
//		try {
//			db.updateAlarmTO(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void testUpdateAlarmTOWithRepeatedAlarmTO() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		RepeatedAlarmTO alarmResult = (RepeatedAlarmTO) db.createAlarmTO(rAlarm);
//		assertTrue(db.getAllAlarmTOs().contains(alarmResult));
//		alarmResult.setInfo("updatedInfo");
//		alarmResult.setRepeatQuantity(8);
//		AlarmTO updatedAlarm = db.updateAlarmTO(alarmResult);
//		RepeatedAlarmTO updatedAlarmResult = (RepeatedAlarmTO) db.getAlarmTOById(updatedAlarm.getAlarmID());
//		assertTrue(db.getAllAlarmTOs().contains(updatedAlarm));
//		assertEquals(updatedAlarm, updatedAlarmResult);
//		assertEquals(updatedAlarmResult.getInfo(), "updatedInfo");
//		assertTrue(updatedAlarmResult.getRepeatQuantity() == 8);
//	}
//
//	public void testGetAllAlarmTOs() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//
//		AlarmTO alarm1 = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm2 = new AlarmTO(6, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm3 = new AlarmTO(7, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//
//		db.createAlarmTO(alarm1);
//		db.createAlarmTO(alarm2);
//		db.createAlarmTO(alarm3);
//
//		List<AlarmTO> alarms = db.getAllAlarmTOs();
//
//		assertTrue(alarms.size() == 3);
//		assertTrue(alarms.contains(alarm1));
//		assertTrue(alarms.contains(alarm2));
//		assertTrue(alarms.contains(alarm3));
//	}
//
//	public void testGetAlarmTOByID() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		db.createAlarmTO(alarm);
//		AlarmTO alarmResult = db.getAlarmTOById(5);
//		assertEquals(alarm, alarmResult);
//	}
//
//	public void testDeleteAlarmTOsByID() throws DatabaseException {
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//
//		AlarmTO alarm1 = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm2 = new AlarmTO(6, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm3 = new AlarmTO(7, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//
//		db.createAlarmTO(alarm1);
//		db.createAlarmTO(alarm2);
//		db.createAlarmTO(alarm3);
//		
//		ArrayList<Long> idsToDelete = new ArrayList<Long>();
//		idsToDelete.add((long) 5);
//		idsToDelete.add((long) 6);
//		idsToDelete.add((long) 7);
//		
//		assertTrue(db.getAllAlarmTOs().size() == 3);
//		
//		db.deleteAlarmTOsByIds(idsToDelete);
//		
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//	}
//
//	public void testDeleteAlarmTOsByIDExceptionWithNullParameter() {
//		try {
//			db.deleteAlarmTOsByIds(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void testDeleteAlarmTOs() throws DatabaseException {
//		AlarmTO alarm1 = new AlarmTO(5, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm2 = new AlarmTO(6, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//		AlarmTO alarm3 = new AlarmTO(7, "testTitle", "testinfo", Calendar.getInstance().getTimeInMillis());
//
//		ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
//		alarms.add(alarm1);
//		alarms.add(alarm2);
//		alarms.add(alarm3);
//		
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		db.storeAlarmTOs(alarms);
//		assertTrue(db.getAllAlarmTOs().size() == 3);
//		
//		db.deleteAlarmTOs(alarms);
//		assertTrue(db.getAllAlarmTOs().isEmpty());
//		
//	}
//
//	public void testDeleteAlarmTOsExceptionWithNullParameter() {
//		try {
//			db.deleteAlarmTOs(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void testTryFuturizationAlarmTOWithFuturizable() throws DatabaseException {
//		
//		RepeatedAlarmTO repeatedAlarm = new RepeatedAlarmTO(alarm);
//		
//		repeatedAlarm.setRepeatUnit(Calendar.MINUTE);
//		repeatedAlarm.setRepeatQuantity(10);
//		
//		Calendar endDate = Calendar.getInstance();
//		endDate.add(Calendar.DATE, 2);
//		
//		repeatedAlarm.setRepeatEnddate(endDate.getTimeInMillis());
//		
//		Calendar date = Calendar.getInstance();
//		date.add(Calendar.MONTH, -1);
//		repeatedAlarm.setDateInMillis(date.getTimeInMillis());
//		
//		Calendar dateResult = Calendar.getInstance();
//		dateResult.setTimeInMillis(repeatedAlarm.getDateInMillis());
//		
//		assertTrue(dateResult.before(Calendar.getInstance()));
//		
//		db.createAlarmTO(repeatedAlarm);
//		
//		assertNotNull(db.getAlarmTOById(5));
//		
//		db.tryFuturizationOfRepeatedAlarmTO(repeatedAlarm);
//		
//		RepeatedAlarmTO repeatedAlarmResult = (RepeatedAlarmTO) db.getAlarmTOById(repeatedAlarm.getAlarmID());
//		
//		Calendar dateFuturized = Calendar.getInstance();
//		dateFuturized.setTimeInMillis(repeatedAlarmResult.getDateInMillis());
//		
//		assertTrue(dateFuturized.after(Calendar.getInstance()));
//	}
//
//	public void testTryFuturizationAlarmTOWithUnfuturizableDeletesAlarm() throws DatabaseException {
//		RepeatedAlarmTO repeatedAlarm = new RepeatedAlarmTO(alarm);
//		repeatedAlarm.setRepeatUnit(Calendar.MINUTE);
//		repeatedAlarm.setRepeatQuantity(10);
//		Calendar endDate = Calendar.getInstance();
//		endDate.add(Calendar.DATE, -5);
//		Calendar date = Calendar.getInstance();
//		date.set(Calendar.MONTH, 4);
//		repeatedAlarm.setDateInMillis(date.getTimeInMillis());
//		
//		Calendar dateResult = Calendar.getInstance();
//		dateResult.setTimeInMillis(repeatedAlarm.getDateInMillis());
//		
//		assertTrue(dateResult.before(Calendar.getInstance()));
//		
//		db.createAlarmTO(repeatedAlarm);
//		
//		assertTrue(db.getAllAlarmTOs().contains(repeatedAlarm));
//		
//		db.tryFuturizationOfRepeatedAlarmTO(repeatedAlarm);
//		
//		assertFalse(db.getAllAlarmTOs().contains(repeatedAlarm));
//	}
//
//	public void testTryFuturizationAlarmTOExceptionWithNullParameter() {
//		try {
//			db.tryFuturizationOfRepeatedAlarmTO(null);
//			fail("Expected a DatabaseException");
//		}
//		catch (DatabaseException e) {
//		}
//	}
//
//	public void tearDown() throws Exception {
//		db.removeAll();
//		db.close();
//		super.tearDown();
//	}
//
//}
