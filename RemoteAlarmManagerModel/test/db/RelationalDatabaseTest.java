/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Alarm;
import entities.User;
import exceptions.DatabaseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author ivarv
 */
    public class RelationalDatabaseTest {
        
       
    
    private static RelationalDatabase db;
    private User user;
    private Alarm alarm;
    
    public RelationalDatabaseTest() {
    }
    
    @Before
    public void setUp() {
        user = new User("Lord", "Foo", "pass", "foo@bar.com", true);
        alarm = new Alarm("Testalarm", "Dit is een testalarm", false, Calendar.getInstance().getTimeInMillis());
        db = RelationalDatabase.getInstance();
    }
    
    @After
    public void tearDown() {
        if(user != null){
            try {
                db.deleteUser(user);
            } catch (DatabaseException ex) {
                Logger.getLogger(RelationalDatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(alarm != null){
            try {
                db.deleteAlarm(alarm);
            } catch (DatabaseException ex) {
                Logger.getLogger(RelationalDatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of createUser method, of class RelationalDatabase.
     */
    @Test
    public void testCreateUser() throws Exception {
        user = db.createUser(user);
        assertNotNull(user.getUserid());
    }

    /**
     * Test of getUser method, of class RelationalDatabase.
     */
    @Test
    public void testGetUser() throws Exception {
        user = db.createUser(user);
        User user2 = db.getUser(user.getUserid());
        assertEquals(user, user2);
    }

    /**
     * Test of deleteUser method, of class RelationalDatabase.
     */
    @Test
    public void testDeleteUser_User() throws Exception {
        user = db.createUser(user);
        int id = user.getUserid();
        db.deleteUser(user);
        User user2 = db.getUser(id);
        assertNull(user2);
    }

    /**
     * Test of deleteUser method, of class RelationalDatabase.
     */
    @Test
    public void testDeleteUser_int() throws Exception {
        user = db.createUser(user);
        int id = user.getUserid();
        db.deleteUser(id);
        User user2 = db.getUser(id);
        assertNull(user2);
    }

    /**
     * Test of updateUser method, of class RelationalDatabase.
     */
    @Test
    public void testUpdateUser() throws Exception {
        user = db.createUser(user);
        user.setNaam("Borf");
        user.setAchternaam("Blarf");
        User user2 = db.updateUser(user);
        User user3 = db.getUser(user.getUserid());
        assertEquals("Borf", user2.getNaam());
        assertEquals("Blarf", user2.getAchternaam());
        assertEquals("Borf", user3.getNaam());
        assertEquals("Blarf", user3.getAchternaam());
    }

    /**
     * Test of getAllUsers method, of class RelationalDatabase.
     */
    @Test
    public void testGetAllUsers() throws Exception{
        user = db.createUser(user);
        List<User> users = db.getAllUsers();
        assertEquals(2, users.size());
    }

    /**
     * Test of getAlarmsFromUser method, of class RelationalDatabase.
     */
    @Test
    public void testGetAlarmsFromUser() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user, alarm);
        List<Alarm> alarms = db.getAlarmsFromUser(user);
        assertEquals(alarm, alarms.get(0));
    }

    /**
     * Test of createAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testCreateAlarm() throws Exception {
        alarm = db.createAlarm(alarm);
        assertNotNull(alarm.getAlarmid());
    }

    /**
     * Test of getAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testGetAlarm() throws Exception {
        alarm = db.createAlarm(alarm);
        int id = alarm.getAlarmid();
        Alarm alarm2 = db.getAlarm(id);
        assertEquals(alarm, alarm2);
    }

    /**
     * Test of deleteAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testDeleteAlarm_Alarm() throws Exception {
        alarm = db.createAlarm(alarm);
        int id = alarm.getAlarmid();
        assertNotNull(id);
        db.deleteAlarm(alarm);
        Alarm alarm2 = db.getAlarm(id);
        assertNull(alarm2);
    }

    /**
     * Test of deleteAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testDeleteAlarm_int() throws Exception {
        alarm = db.createAlarm(alarm);
        int id = alarm.getAlarmid();
        assertNotNull(id);
        db.deleteAlarm(id);
        Alarm alarm2 = db.getAlarm(id);
        assertNull(alarm2);
    }

    /**
     * Test of updateAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testUpdateAlarm() throws Exception {
        alarm = db.createAlarm(alarm);
        assertNotNull(alarm.getAlarmid());
        alarm.setTitle("Blor");
        alarm.setInfo("Blier");
        db.updateAlarm(alarm);
        Alarm alarm2 = db.getAlarm(alarm.getAlarmid());
        assertEquals("Blor", alarm2.getTitle());
        assertEquals("Blier", alarm2.getInfo());
    }

    /**
     * Test of getAllAlarms method, of class RelationalDatabase.
     */
    @Test
    public void testGetAllAlarms() {
        List<Alarm> alarms = db.getAllAlarms();
        assertEquals(alarms.size(), alarms.size());
    }

    /**
     * Test of getUsersFromAlarm method, of class RelationalDatabase.
     */
    @Test
    public void testGetUsersFromAlarm() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user, alarm);
        List<User> users = db.getUsersFromAlarm(alarm);
        assertTrue(users.contains(user));
    }

    /**
     * Test of addAlarmToUser method, of class RelationalDatabase.
     */
    @Test
    public void testAddAlarmToUser_User_Alarm() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user, alarm);
        List<Alarm> alarms = db.getAlarmsFromUser(user);
        assertTrue(alarms.contains(alarm));
    }

    /**
     * Test of addAlarmToUser method, of class RelationalDatabase.
     */
    @Test
    public void testAddAlarmToUser_int_Alarm() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user.getUserid(), alarm.getAlarmid());
        List<Alarm> alarms = db.getAlarmsFromUser(user);
        assertTrue(alarms.contains(alarm));
    }

    /**
     * Test of removeAlarmFromUser method, of class RelationalDatabase.
     */
    @Test
    public void testRemoveAlarmFromUser_User_Alarm() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user.getUserid(), alarm.getAlarmid());
        List<Alarm> alarms = db.getAlarmsFromUser(user);
        assertTrue(alarms.contains(alarm));
        db.removeAlarmFromUser(user, alarm);
        List<Alarm> alarms2 = db.getAlarmsFromUser(user);
        assertFalse(alarms2.contains(alarm));
    }

    /**
     * Test of removeAlarmFromUser method, of class RelationalDatabase.
     */
    @Test
    public void testRemoveAlarmFromUser_int_int() throws Exception {
        user = db.createUser(user);
        alarm = db.createAlarm(alarm);
        db.addAlarmToUser(user.getUserid(), alarm.getAlarmid());
        List<Alarm> alarms = db.getAlarmsFromUser(user);
        assertTrue(alarms.contains(alarm));
        db.removeAlarmFromUser(user.getUserid(), alarm.getAlarmid());
        List<Alarm> alarms2 = db.getAlarmsFromUser(user);
        assertFalse(alarms2.contains(alarm));
    }

    /**
     * Test of cleanUpAlarms method, of class RelationalDatabase.
     */
    @Test
    public void testCleanUpAlarms() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        Alarm alarm = new Alarm("Blar", "blar", false, cal.getTimeInMillis());
        db.createAlarm(alarm);
        int numberOfAlarms = db.getAllAlarms().size();
        db.cleanUpAlarms();
        int newNumberOfAlarms = db.getAllAlarms().size();
        assertTrue(numberOfAlarms > newNumberOfAlarms);
    }
}