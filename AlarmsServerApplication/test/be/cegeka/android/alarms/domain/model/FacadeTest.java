package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.Collection;
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
    private Facade facade;
    private UserTO userTO;
    private User user;
    private AlarmTO alarmTO;
    private Alarm alarm;
    private String emailadres;
    @Mock
    private Service serviceMock;
    @Mock
    private TransferObjectMapper transferObjectMapperMock;


    @Before
    public void setUp() throws BusinessException
    {
        facade = new Facade();
        facade.setService(serviceMock);
        facade.setTransferObjectMapper(transferObjectMapperMock);

        userTO = new UserTO(25435, "testUserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        alarmTO = new AlarmTO(1354, "testAlarmTitle", "testAlarmInfo", 156123);

        user = new User(25435, "testUserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        alarm = new Alarm(1354, "testAlarmTitle", "testAlarmInfo", 156123);

        emailadres = "testUserEmail";
    }


    @Test
    public void testGetUser() throws BusinessException
    {
        when(serviceMock.getUser(emailadres)).thenReturn(user);
        when(transferObjectMapperMock.convertUserToUserTO(user)).thenReturn(userTO);

        UserTO resultTO = facade.getUser(emailadres);
        assertEquals(userTO, resultTO);
    }


    @Test
    public void testGetAllUsers() throws BusinessException
    {
        Collection<User> users = new ArrayList<>();
        User user1 = new User(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        User user2 = new User(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        User user3 = new User(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Collection<UserTO> usersTOs = new ArrayList<>();
        UserTO userTO1 = new UserTO(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        UserTO userTO2 = new UserTO(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        UserTO userTO3 = new UserTO(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        usersTOs.add(userTO1);
        usersTOs.add(userTO2);
        usersTOs.add(userTO3);

        when(serviceMock.getAllUsers()).thenReturn(users);
        when(transferObjectMapperMock.convertUserToUserTO(user1)).thenReturn(userTO1);
        when(transferObjectMapperMock.convertUserToUserTO(user2)).thenReturn(userTO2);
        when(transferObjectMapperMock.convertUserToUserTO(user3)).thenReturn(userTO3);

        Collection<UserTO> resultedUserTOs = facade.getAllUsers();

        assertEquals(usersTOs, resultedUserTOs);
    }


    @Test
    public void testGetAllAlarms() throws BusinessException
    {
        Collection<Alarm> alarms = new ArrayList<>();
        Alarm alarm1 = new Alarm(1, "testAlarmTitle", "testAlarmInfo", 156123);
        Alarm alarm2 = new Alarm(2, "testAlarmTitle", "testAlarmInfo", 156123);
        Alarm alarm3 = new Alarm(3, "testAlarmTitle", "testAlarmInfo", 156123);
        alarms.add(alarm1);
        alarms.add(alarm2);
        alarms.add(alarm3);

        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        AlarmTO alarmTO1 = new AlarmTO(1, "testAlarmTitle", "testAlarmInfo", 156123);
        AlarmTO alarmTO2 = new AlarmTO(2, "testAlarmTitle", "testAlarmInfo", 156123);
        AlarmTO alarmTO3 = new AlarmTO(3, "testAlarmTitle", "testAlarmInfo", 156123);
        alarmTOs.add(alarmTO1);
        alarmTOs.add(alarmTO2);
        alarmTOs.add(alarmTO3);

        when(serviceMock.getAllAlarms()).thenReturn(alarms);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm1)).thenReturn(alarmTO1);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm2)).thenReturn(alarmTO2);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm3)).thenReturn(alarmTO3);

        Collection<AlarmTO> resultedAlarmTOs = facade.getAllAlarms();

        assertEquals(alarmTOs, resultedAlarmTOs);

    }


    @Test
    public void testGetUsersForAlarm() throws BusinessException
    {
        Collection<UserTO> userTOs = new ArrayList<>();
        userTOs.add(userTO);
        Collection<User> users = new ArrayList<>();
        users.add(user);
        
        alarm.addUser(user);
        
        when(serviceMock.getUsersForAlarm(alarm)).thenReturn(users);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm)).thenReturn(alarmTO);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);
        when(transferObjectMapperMock.convertUserToUserTO(user)).thenReturn(userTO);
        when(transferObjectMapperMock.convertUserTOToUser(userTO)).thenReturn(user);

        Collection<UserTO> resultedUserTOs = facade.getUsersForAlarm(alarmTO);
        
        assertEquals(userTOs, resultedUserTOs);
    }


    @Test
    public void testGetAlarmsForUser() throws BusinessException
    {
        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        alarmTOs.add(alarmTO);
        Collection<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);
        
        user.addAlarm(alarm);
        
        when(serviceMock.getAlarmsForUser(user)).thenReturn(alarms);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm)).thenReturn(alarmTO);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);
        when(transferObjectMapperMock.convertUserToUserTO(user)).thenReturn(userTO);
        when(transferObjectMapperMock.convertUserTOToUser(userTO)).thenReturn(user);

        Collection<AlarmTO> resultedAlarmTOs = facade.getAlarmsForUser(userTO);
        
        assertEquals(alarmTOs, resultedAlarmTOs);
    }


    @Test
    public void testAddUser() throws Exception
    {
        when(transferObjectMapperMock.convertUserTOToUser(userTO)).thenReturn(user);
        when(serviceMock.addUser(any(User.class))).thenReturn(user);
        UserTO resultedTO = mock(UserTO.class);
        when(transferObjectMapperMock.convertUserToUserTO(user)).thenReturn(resultedTO);
        
        UserTO result = facade.addUser(userTO);
        assertSame(resultedTO, result);
        
        verify(serviceMock).addUser(user);
    }


    @Test
    public void testAddAlarm() throws Exception
    {
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);
        when(serviceMock.addAlarm(Mockito.any(Alarm.class))).thenReturn(alarm);
        AlarmTO resultedTO = mock(AlarmTO.class);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm)).thenReturn(resultedTO);

        AlarmTO result = facade.addAlarm(alarmTO);
        assertSame(resultedTO, result);

        verify(serviceMock).addAlarm(alarm);
    }


    @Test
    public void testAddUsers() throws Exception
    {
        Collection<User> users = new ArrayList<>();
        User user1 = new User(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        User user2 = new User(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        User user3 = new User(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Collection<UserTO> usersTOs = new ArrayList<>();
        UserTO userTO1 = new UserTO(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        UserTO userTO2 = new UserTO(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        UserTO userTO3 = new UserTO(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        usersTOs.add(userTO1);
        usersTOs.add(userTO2);
        usersTOs.add(userTO3);

        when(transferObjectMapperMock.convertUserTOToUser(userTO1)).thenReturn(user1);
        when(transferObjectMapperMock.convertUserTOToUser(userTO2)).thenReturn(user2);
        when(transferObjectMapperMock.convertUserTOToUser(userTO3)).thenReturn(user3);
        
        facade.addUsers(usersTOs);
        verify(serviceMock).addUsers(users);
    }


    @Test
    public void testAddAlarms() throws Exception
    {
        Collection<Alarm> alarms = new ArrayList<>();
        Alarm alarm1 = new Alarm(1, "testAlarmTitle", "testAlarmInfo", 156123);
        Alarm alarm2 = new Alarm(2, "testAlarmTitle", "testAlarmInfo", 15612);
        Alarm alarm3 = new Alarm(3, "testAlarmTitle", "testAlarmInfo", 1561);
        alarms.add(alarm1);
        alarms.add(alarm2);
        alarms.add(alarm3);

        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        AlarmTO alarmTO1 = new AlarmTO(1, "testAlarmTitle", "testAlarmInfo", 156123);
        AlarmTO alarmTO2 = new AlarmTO(2, "testAlarmTitle", "testAlarmInfo", 15612);
        AlarmTO alarmTO3 = new AlarmTO(3, "testAlarmTitle", "testAlarmInfo", 1561);
        alarmTOs.add(alarmTO1);
        alarmTOs.add(alarmTO2);
        alarmTOs.add(alarmTO3);
        
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO1)).thenReturn(alarm1);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO2)).thenReturn(alarm2);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO3)).thenReturn(alarm3);
        
        facade.addAlarms(alarmTOs);
        verify(serviceMock).addAlarms(alarms);
    }


    @Test
    public void testUpdateUser() throws Exception
    {
        when(transferObjectMapperMock.convertUserTOToUser(userTO)).thenReturn(user);
        when(transferObjectMapperMock.convertUserToUserTO(user)).thenReturn(userTO);
        when(serviceMock.updateUser(user)).thenReturn(user);

        user.setAdmin(true);
        user.setPaswoord("nieuwPaswoord");
        user.setAchternaam("nieuweAchternaam");
        user.setEmail("nieuweEmail");
        user.setNaam("nieuweNaam");
        
        userTO.setAdmin(true);
        userTO.setPaswoord("nieuwPaswoord");
        userTO.setAchternaam("nieuweAchternaam");
        userTO.setEmail("nieuweEmail");
        userTO.setNaam("nieuweNaam");
        
        UserTO resultedUserTO = facade.updateUser(userTO);
        assertEquals(userTO, resultedUserTO);
    }


    @Test
    public void testUpdateAlarm() throws Exception
    {
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);
        when(transferObjectMapperMock.convertAlarmToAlarmTO(alarm)).thenReturn(alarmTO);
        when(serviceMock.updateAlarm(alarm)).thenReturn(alarm);
        
        alarm.setInfo("nieuweInfo");
        alarm.setTitle("nieuweTitel");
        
        alarmTO.setInfo("nieuweInfo");
        alarmTO.setTitle("nieuweTitel");
        
        AlarmTO resultedAlarmTO = facade.updateAlarm(alarmTO);
        assertEquals(alarmTO, resultedAlarmTO);
    }


    @Test
    public void testDeleteUser() throws Exception
    {
        when(transferObjectMapperMock.convertUserTOToUser(userTO)).thenReturn(user);
        
        facade.deleteUser(userTO);
        
        verify(serviceMock).deleteUser(user);
    }


    @Test
    public void testDeleteAlarm() throws Exception
    {
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO)).thenReturn(alarm);
        
        facade.deleteAlarm(alarmTO);
        
        verify(serviceMock).deleteAlarm(alarm);
    }


    @Test
    public void testDeleteUsers() throws Exception
    {
        Collection<User> users = new ArrayList<>();
        User user1 = new User(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        User user2 = new User(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        User user3 = new User(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Collection<UserTO> usersTOs = new ArrayList<>();
        UserTO userTO1 = new UserTO(1, "testUserNaam1", "testAchternaam1", "testUserPaswoord1", "testUserEmail1", true);
        UserTO userTO2 = new UserTO(2, "testUserNaam2", "testAchternaam2", "testUserPaswoord2", "testUserEmail2", true);
        UserTO userTO3 = new UserTO(3, "testUserNaam3", "testAchternaam3", "testUserPaswoord3", "testUserEmail3", true);
        usersTOs.add(userTO1);
        usersTOs.add(userTO2);
        usersTOs.add(userTO3);

        when(transferObjectMapperMock.convertUserTOToUser(userTO1)).thenReturn(user1);
        when(transferObjectMapperMock.convertUserTOToUser(userTO2)).thenReturn(user2);
        when(transferObjectMapperMock.convertUserTOToUser(userTO3)).thenReturn(user3);
        
        facade.deleteUsers(usersTOs);
        verify(serviceMock).deleteUsers(users);
    }


    @Test
    public void testDeleteAlarms() throws Exception
    {
        Collection<Alarm> alarms = new ArrayList<>();
        Alarm alarm1 = new Alarm(1, "testAlarmTitle", "testAlarmInfo", 156123);
        Alarm alarm2 = new Alarm(2, "testAlarmTitle", "testAlarmInfo", 15612);
        Alarm alarm3 = new Alarm(3, "testAlarmTitle", "testAlarmInfo", 1561);
        alarms.add(alarm1);
        alarms.add(alarm2);
        alarms.add(alarm3);

        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        AlarmTO alarmTO1 = new AlarmTO(1, "testAlarmTitle", "testAlarmInfo", 156123);
        AlarmTO alarmTO2 = new AlarmTO(2, "testAlarmTitle", "testAlarmInfo", 15612);
        AlarmTO alarmTO3 = new AlarmTO(3, "testAlarmTitle", "testAlarmInfo", 1561);
        alarmTOs.add(alarmTO1);
        alarmTOs.add(alarmTO2);
        alarmTOs.add(alarmTO3);
        
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO1)).thenReturn(alarm1);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO2)).thenReturn(alarm2);
        when(transferObjectMapperMock.convertAlarmTOToAlarm(alarmTO3)).thenReturn(alarm3);
        
        facade.deleteAlarms(alarmTOs);
        verify(serviceMock).deleteAlarms(alarms);
    }
}