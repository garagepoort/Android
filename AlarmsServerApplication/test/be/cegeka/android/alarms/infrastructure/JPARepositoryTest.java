/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author hannesc
 */
public class JPARepositoryTest
{
    private JPARepository jpaRepository;
    private User testUser;
    private Alarm testAlarm;
    private RepeatedAlarm testRepeatedAlarm;


    /**
     *
     */
    @Before
    public void setUp()
    {
        jpaRepository = new JPARepository();
        testUser = new User("testuserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        testAlarm = new Alarm("testalarm", "testalarm info", 150000);
        testRepeatedAlarm = new RepeatedAlarm(Calendar.DAY_OF_MONTH, 200, 1858952, "testrepeatedalarmtitel", "repeatedInfo", 8748979);
        try
        {
            testUser.addAlarm(testAlarm);
            testUser.addAlarm(testRepeatedAlarm);
//            testAlarm.addUser(testUser);
            testAlarm = jpaRepository.addAlarm(testAlarm);
            testRepeatedAlarm = (RepeatedAlarm) jpaRepository.addAlarm(testRepeatedAlarm);
            testUser = jpaRepository.addUser(testUser);
        }
        catch (DatabaseException ex)
        {
            Logger.getLogger(JPARepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @After
    public void tearDown()
    {
        try
        {
//            if (instance.entityManager.getTransaction().isActive()) {
//                   instance.entityManager.getTransaction().commit();
            jpaRepository.deleteAlarm(testRepeatedAlarm);
            jpaRepository.deleteAlarm(testAlarm);
            jpaRepository.deleteUser(testUser);
        }
        catch (DatabaseException ex)
        {
            Logger.getLogger(JPARepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Test of getUser method, of class JPARepository.
     */
    @Test
    public void testGetUser()
    {
        String emailadres = "testUserEmail";
        User result = jpaRepository.getUser(emailadres);
        assertEquals(testUser, result);
        assertTrue(result.getAlarms().contains(testAlarm));
        for(Alarm a : result.getAlarms()){
            assertTrue(a.getUsers().contains(result));
        }
    }
    
    /**
     * Test of getUser method, of class JPARepository.
     */
    @Test
    public void testGetAlarm()
    {
        Alarm result = jpaRepository.getAlarm(testAlarm.getAlarmid());
        assertEquals(testAlarm, result);
        assertTrue(result.getUsers().contains(testUser));
        for(User u : result.getUsers()){
            assertTrue(u.getAlarms().contains(result));
        }
    }
    
     /**
     * Test of getUser method, of class JPARepository.
     */
    @Test
    public void testGetRepeatedAlarm()
    {
        RepeatedAlarm result = (RepeatedAlarm) jpaRepository.getAlarm(testRepeatedAlarm.getAlarmid());
        assertEquals(testRepeatedAlarm, result);
        assertEquals(result.getRepeatEnddate(), testRepeatedAlarm.getRepeatEnddate());
        assertTrue(result.getUsers().contains(testUser));
    }


    /**
     * Test of getAllUsers method, of class JPARepository.
     */
    @Test
    public void testGetAllUsers()
    {
        Collection result = jpaRepository.getAllUsers();
        assertTrue(result.contains(testUser));
    }


    /**
     * Test of getAllAlarms method, of class JPARepository.
     */
    @Test
    public void testGetAllAlarms()
    {
        Collection result = jpaRepository.getAllAlarms();
        assertTrue(result.contains(testAlarm));
    }


    /**
     * Test of getUsersForAlarm method, of class JPARepository.
     */
    @Test
    public void testGetUsersForAlarm()
    {
        Collection result = jpaRepository.getUsersForAlarm(testAlarm);
        assertTrue(new ArrayList<User>(result).contains(testUser));
    }


    /**
     * Test of getAlarmsForUser method, of class JPARepository.
     */
    @Test
    public void testGetAlarmsForUser()
    {
        Collection result = jpaRepository.getAlarmsForUser(testUser);
        assertTrue(new ArrayList<Alarm>(result).contains(testAlarm));
    }


    /**
     * Test of addUser method, of class JPARepository.
     */
    @Test
    public void testAddUser() throws Exception
    {
        assertTrue(jpaRepository.getAllUsers().contains(testUser));
    }


    /**
     * Test of addAlarm method, of class JPARepository.
     */
    @Test
    public void testAddAlarm() throws Exception
    {
        assertTrue(jpaRepository.getAllAlarms().contains(testAlarm));
    }

    @Test
    public void testAddRepeatedAlarm()
    {
        jpaRepository.getAllAlarms().contains(testRepeatedAlarm);
    }

    /**
     * Test of addUsers method, of class JPARepository.
     */
    @Test
    public void testAddUsers() throws Exception
    {
        User u1 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u2 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u3 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u4 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        jpaRepository.addUsers(users);
        assertTrue(jpaRepository.getAllUsers().contains(u1));
        assertTrue(jpaRepository.getAllUsers().contains(u2));
        assertTrue(jpaRepository.getAllUsers().contains(u3));
        assertTrue(jpaRepository.getAllUsers().contains(u4));
        jpaRepository.deleteUsers(users);
    }


    @Test
    public void testAddAlarms() throws Exception
    {
        ArrayList<Alarm> alarms = new ArrayList<>();
        Alarm a1 = new Alarm("titel", "ingop", 855151);
        Alarm a2 = new Alarm("titel", "ingop", 855151);
        Alarm a3 = new Alarm("titel", "ingop", 855151);
        Alarm a4 = new Alarm("titel", "ingop", 855151);
        alarms.add(a1);
        alarms.add(a2);
        alarms.add(a3);
        alarms.add(a4);
        jpaRepository.addAlarms(alarms);
        assertTrue(jpaRepository.getAllAlarms().contains(a1));
        assertTrue(jpaRepository.getAllAlarms().contains(a2));
        assertTrue(jpaRepository.getAllAlarms().contains(a3));
        assertTrue(jpaRepository.getAllAlarms().contains(a4));
        jpaRepository.deleteAlarms(alarms);
    }


    /**
     * Test of updateUser method, of class JPARepository.
     */
    @Test
    public void testUpdateUser() throws Exception
    {
        Alarm alarm2 = new Alarm("newalarm", "info", 4588887);
        testUser.addAlarm(alarm2);
        testUser.setNaam("nieuwe naam");
        User newUser = jpaRepository.updateUser(testUser);
        assertTrue(newUser.getNaam().equals(testUser.getNaam()));
        assertEquals(3, newUser.getAlarms().size());
    }


    /**
     * Test of updateAlarm method, of class JPARepository.
     */
    @Test
    public void testUpdateAlarm() throws Exception
    {
        User user2 = new User("", "", "", "", Boolean.TRUE);
        testAlarm.addUser(user2);
        testAlarm.setInfo("new info");
        Alarm newAlarm = jpaRepository.updateAlarm(testAlarm);
        assertTrue(newAlarm.getInfo().equals(testAlarm.getInfo()));
        assertEquals(2, newAlarm.getUsers().size());
    }


    /**
     * Test of deleteUser method, of class JPARepository.
     */
    @Test
    public void testDeleteUser() throws Exception
    {
        jpaRepository.deleteUser(testUser);
        assertNull(jpaRepository.getUser(testUser.getEmail()));
    }


    /**
     * Test of deleteAlarm method, of class JPARepository.
     */
    @Test
    public void testDeleteAlarm() throws Exception
    {
        jpaRepository.deleteAlarm(testAlarm);
        assertFalse(jpaRepository.getAllAlarms().contains(testAlarm));

    }


    /**
     * Test of deleteUsers method, of class JPARepository.
     */
    @Test
    public void testDeleteUsers() throws Exception
    {
        User u1 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u2 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u3 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        User u4 = new User("dez", "oezfoiezfe", "fezfezezhfe", "dzazdzedfzefez", Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        jpaRepository.addUsers(users);
        assertTrue(jpaRepository.getAllUsers().contains(u1));
        assertTrue(jpaRepository.getAllUsers().contains(u2));
        assertTrue(jpaRepository.getAllUsers().contains(u3));
        assertTrue(jpaRepository.getAllUsers().contains(u4));
        jpaRepository.deleteUsers(users);
        assertFalse(jpaRepository.getAllUsers().contains(u1));
        assertFalse(jpaRepository.getAllUsers().contains(u2));
        assertFalse(jpaRepository.getAllUsers().contains(u3));
        assertFalse(jpaRepository.getAllUsers().contains(u4));
    }


    @Test
    public void whenUserDeleted_ThenDoNotDeleteAlarm()
    {
        try
        {
            jpaRepository.deleteUser(testUser);
            assertTrue(jpaRepository.getAllAlarms().contains(testAlarm));
        }
        catch (DatabaseException ex)
        {
            Logger.getLogger(JPARepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    /**
     * Test of deleteAlarms method, of class JPARepository.
     */
    @Test
    public void testDeleteAlarms() throws Exception
    {
        ArrayList<Alarm> alarms = new ArrayList<>();
        Alarm a1 = new Alarm("titel", "ingop", 855151);
        Alarm a2 = new Alarm("titel", "ingop", 855151);
        Alarm a3 = new Alarm("titel", "ingop", 855151);
        Alarm a4 = new Alarm("titel", "ingop", 855151);
        alarms.add(a1);
        alarms.add(a2);
        alarms.add(a3);
        alarms.add(a4);
        jpaRepository.addAlarms(alarms);
        assertTrue(jpaRepository.getAllAlarms().contains(a1));
        assertTrue(jpaRepository.getAllAlarms().contains(a2));
        assertTrue(jpaRepository.getAllAlarms().contains(a3));
        assertTrue(jpaRepository.getAllAlarms().contains(a4));
        jpaRepository.deleteAlarms(alarms);
        assertFalse(jpaRepository.getAllAlarms().contains(a1));
        assertFalse(jpaRepository.getAllAlarms().contains(a2));
        assertFalse(jpaRepository.getAllAlarms().contains(a3));
        assertFalse(jpaRepository.getAllAlarms().contains(a4));
    }
    
    
    @Test
    public void whenAlarmDeleted_ThenDoNotDeleteUser()
    {
        try
        {
            jpaRepository.deleteAlarm(testAlarm);
            assertTrue(jpaRepository.getAllUsers().contains(testUser));
        }
        catch (DatabaseException ex)
        {
            Logger.getLogger(JPARepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}