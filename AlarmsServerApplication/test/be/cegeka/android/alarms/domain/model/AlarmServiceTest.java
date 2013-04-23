package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AlarmServiceTest
{
    AlarmService alarmService;
    UserTO user;
    AlarmTO alarm;
    String emailadres;        
    
            
    @Before
    public void setUp() throws BusinessException
    {
        alarmService = new AlarmService();
        user = new UserTO(null, "testUserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        alarm = new AlarmTO(null, "testAlarmTitle", "testAlarmInfo", 156123);
        emailadres = "testUserEmail";
        alarmService.addUser(user);
        alarmService.addAlarm(alarm);
        
    }
    

    @After
    public void tearDown()
    {
    }


    @Test
    public void testGetUser() throws BusinessException
    {
        UserTO userTO = alarmService.getUser(emailadres);
        
        assertEquals(user, userTO);
    }


    @Test
    public void testGetAllUsers() throws BusinessException
    {
        List<UserTO> userTOs = new ArrayList<>(alarmService.getAllUsers());
        assertEquals(user, userTOs.get(0));
    }


    @Test
    public void testGetAllAlarms() throws BusinessException
    {
        List<AlarmTO> alarmTOs = new ArrayList<>(alarmService.getAllAlarms());
        assertEquals(alarm, alarmTOs.get(0));
    }


    @Test
    public void testGetUsersForAlarm()
    {
        fail();
    }


    @Test
    public void testGetAlarmsForUser()
    {
        fail();
    }


    @Test
    public void testAddUser() throws Exception
    {
        UserTO userTO = alarmService.getUser(user.getEmail());
        assertEquals(user, userTO);
    }


    @Test
    public void testAddAlarm() throws Exception
    {
        fail();
    }


    @Test
    public void testAddUsers() throws Exception
    {
        assertTrue(false);
    }


    @Test
    public void testAddAlarms() throws Exception
    {
        assertTrue(false);
    }


    @Test
    public void testUpdateUser() throws Exception
    {
        fail();
    }


    @Test
    public void testUpdateAlarm() throws Exception
    {
        fail();
    }


    @Test
    public void testDeleteUser() throws Exception
    {
        fail();
    }


    @Test
    public void testDeleteAlarm() throws Exception
    {
        fail();
    }


    @Test
    public void testDeleteUsers() throws Exception
    {
        fail();
    }


    @Test
    public void testDeleteAlarms() throws Exception
    {
        fail();
    }
}