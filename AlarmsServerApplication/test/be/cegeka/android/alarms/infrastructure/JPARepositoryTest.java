/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    private JPARepository instance;
    private User testUser;
    private Alarm testAlarm;


    /**
     *
     */
    @Before
    public void setUp()
    {
        instance = new JPARepository();
        testUser = new User("testuserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        testAlarm = new Alarm("testalarm", "testalarm info", 150000);
        try
        {
            testUser.addAlarm(testAlarm);
//            testAlarm.addUser(testUser);
            testAlarm = instance.addAlarm(testAlarm);
            testUser = instance.addUser(testUser);
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
//            }
            instance.deleteAlarm(testAlarm);
            instance.deleteUser(testUser);
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
        User result = instance.getUser(emailadres);
        assertEquals(testUser, result);
        assertTrue(testUser.getAlarms().contains(testAlarm));
    }


    /**
     * Test of getAllUsers method, of class JPARepository.
     */
    @Test
    public void testGetAllUsers()
    {
        Collection result = instance.getAllUsers();
        assertTrue(result.contains(testUser));
    }


    /**
     * Test of getAllAlarms method, of class JPARepository.
     */
    @Test
    public void testGetAllAlarms()
    {
        Collection result = instance.getAllAlarms();
        assertTrue(result.contains(testAlarm));
    }


    /**
     * Test of getUsersForAlarm method, of class JPARepository.
     */
    @Test
    public void testGetUsersForAlarm()
    {
        Collection result = instance.getUsersForAlarm(testAlarm);
        assertTrue(new ArrayList<User>(result).contains(testUser));
    }


    /**
     * Test of getAlarmsForUser method, of class JPARepository.
     */
    @Test
    public void testGetAlarmsForUser()
    {
        Collection result = instance.getAlarmsForUser(testUser);
        assertTrue(new ArrayList<Alarm>(result).contains(testAlarm));
    }


    /**
     * Test of addUser method, of class JPARepository.
     */
    @Test
    public void testAddUser() throws Exception
    {
        assertTrue(instance.getAllUsers().contains(testUser));
    }


    /**
     * Test of addAlarm method, of class JPARepository.
     */
    @Test
    public void testAddAlarm() throws Exception
    {;
        assertTrue(instance.getAllAlarms().contains(testAlarm));
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
        instance.addUsers(users);
        assertTrue(instance.getAllUsers().contains(u1));
        assertTrue(instance.getAllUsers().contains(u2));
        assertTrue(instance.getAllUsers().contains(u3));
        assertTrue(instance.getAllUsers().contains(u4));
        instance.deleteUsers(users);
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
        instance.addAlarms(alarms);
        assertTrue(instance.getAllAlarms().contains(a1));
        assertTrue(instance.getAllAlarms().contains(a2));
        assertTrue(instance.getAllAlarms().contains(a3));
        assertTrue(instance.getAllAlarms().contains(a4));
        instance.deleteAlarms(alarms);
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
        User newUser = instance.updateUser(testUser);
        assertTrue(newUser.getNaam().equals(testUser.getNaam()));
        assertEquals(2, newUser.getAlarms().size());
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
        Alarm newAlarm = instance.updateAlarm(testAlarm);
        assertTrue(newAlarm.getInfo().equals(testAlarm.getInfo()));
        assertEquals(2, newAlarm.getUsers().size());
    }


    /**
     * Test of deleteUser method, of class JPARepository.
     */
    @Test
    public void testDeleteUser() throws Exception
    {
        instance.deleteUser(testUser);
        assertNull(instance.getUser(testUser.getEmail()));
    }


    /**
     * Test of deleteAlarm method, of class JPARepository.
     */
    @Test
    public void testDeleteAlarm() throws Exception
    {
        instance.deleteAlarm(testAlarm);
        assertFalse(instance.getAllAlarms().contains(testAlarm));

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
        instance.addUsers(users);
        assertTrue(instance.getAllUsers().contains(u1));
        assertTrue(instance.getAllUsers().contains(u2));
        assertTrue(instance.getAllUsers().contains(u3));
        assertTrue(instance.getAllUsers().contains(u4));
        instance.deleteUsers(users);
        assertFalse(instance.getAllUsers().contains(u1));
        assertFalse(instance.getAllUsers().contains(u2));
        assertFalse(instance.getAllUsers().contains(u3));
        assertFalse(instance.getAllUsers().contains(u4));
    }


    @Test
    public void whenUserDeleted_ThenDoNotDeleteAlarm()
    {
        try
        {
            instance.deleteUser(testUser);
            assertTrue(instance.getAllAlarms().contains(testAlarm));
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
        instance.addAlarms(alarms);
        assertTrue(instance.getAllAlarms().contains(a1));
        assertTrue(instance.getAllAlarms().contains(a2));
        assertTrue(instance.getAllAlarms().contains(a3));
        assertTrue(instance.getAllAlarms().contains(a4));
        instance.deleteAlarms(alarms);
        assertFalse(instance.getAllAlarms().contains(a1));
        assertFalse(instance.getAllAlarms().contains(a2));
        assertFalse(instance.getAllAlarms().contains(a3));
        assertFalse(instance.getAllAlarms().contains(a4));
    }
    
    
    @Test
    public void whenAlarmDeleted_ThenDoNotDeleteUser()
    {
        try
        {
            instance.deleteAlarm(testAlarm);
            assertTrue(instance.getAllUsers().contains(testUser));
        }
        catch (DatabaseException ex)
        {
            Logger.getLogger(JPARepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}