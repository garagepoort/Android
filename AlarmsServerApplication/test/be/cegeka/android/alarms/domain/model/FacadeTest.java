package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class FacadeTest
{
    Facade alarmService;
    UserTO userTO;
    AlarmTO alarmTO;
    String emailadres;      
    @Mock
    private Service serviceMock;
    @Mock
    private TransferObjectMapper transferObjectMapperMock;
    
    private Alarm alarm;
    
            
    @Before
    public void setUp() throws BusinessException
    {
        alarmService = new Facade();
        alarmService.setService(serviceMock);
        alarmService.setTransferObjectMapper(transferObjectMapperMock);
        
        userTO = new UserTO(25435, "testUserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        alarmTO = new AlarmTO(1354, "testAlarmTitle", "testAlarmInfo", 156123);
        
        alarm = new Alarm(1354, "testAlarmTitle", "testAlarmInfo", 156123);
        
        emailadres = "testUserEmail";
    }

    @After
    public void tearDown()
    {
    }


    @Test
    public void testGetUser() throws BusinessException
    {
        UserTO userTO = alarmService.getUser(emailadres);
        
        assertEquals(this.userTO, userTO);
    }


    @Test
    public void testGetAllUsers() throws BusinessException
    {
        List<UserTO> userTOs = new ArrayList<>(alarmService.getAllUsers());
        assertEquals(userTO, userTOs.get(0));
    }


    @Test
    public void testGetAllAlarms() throws BusinessException
    {
        List<AlarmTO> alarmTOs = new ArrayList<>(alarmService.getAllAlarms());
        assertEquals(alarmTO, alarmTOs.get(0));
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
        UserTO userTO = alarmService.getUser(this.userTO.getEmail());
        assertEquals(this.userTO, userTO);
    }


    @Test
    public void testAddAlarm() throws Exception
    {
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);        
        when(serviceMock.addAlarm(Mockito.any(Alarm.class))).thenReturn(alarm);
        AlarmTO resultedTO = mock(AlarmTO.class);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm)).thenReturn(resultedTO);        
        
        AlarmTO result = alarmService.addAlarm(alarmTO);
        assertSame(resultedTO, result);
        
        verify(serviceMock).addAlarm(alarm);
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