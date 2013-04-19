/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
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


    @Before
    public void setUp()
    {
        instance = new JPARepository();
        testUser = new User("testuserNaam", "testAchternaam", "testUserPaswoord", "testUserEmail", true);
        testAlarm = new Alarm("testalarm", "testalarm info", 150000);
        try
        {
            testAlarm  = instance.addAlarm(testAlarm);
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
            instance.deleteUser(testUser);
            instance.deleteAlarm(testAlarm);
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
    }


    /**
     * Test of getAllUsers method, of class JPARepository.
     */
    @Test
    public void testGetAllUsers()
    {
        System.out.println("getAllUsers");
        Collection expResult = null;
        Collection result = instance.getAllUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of getAllAlarms method, of class JPARepository.
     */
    @Test
    public void testGetAllAlarms()
    {
        Collection result = instance.getAllAlarms();
        assertEquals(Arrays.asList(result).get(0), testAlarm);
    }


    /**
     * Test of getUsersForAlarm method, of class JPARepository.
     */
    @Test
    public void testGetUsersForAlarm()
    {
        System.out.println("getUsersForAlarm");
        Alarm alarm = null;
        Collection expResult = null;
        Collection result = instance.getUsersForAlarm(alarm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of getAlarmsForUser method, of class JPARepository.
     */
    @Test
    public void testGetAlarmsForUser()
    {
        System.out.println("getAlarmsForUser");
        User user = null;
        Collection expResult = null;
        Collection result = instance.getAlarmsForUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of addUser method, of class JPARepository.
     */
    @Test
    public void testAddUser() throws Exception
    {
        User u = instance.addUser(testUser);
        assertEquals(testUser, u);
    }


    /**
     * Test of addAlarm method, of class JPARepository.
     */
    @Test
    public void testAddAlarm() throws Exception
    {
        Alarm a = instance.addAlarm(testAlarm);
        assertEquals(testAlarm, a);
    }


    /**
     * Test of addUsers method, of class JPARepository.
     */
    @Test
    public void testAddUsers() throws Exception
    {
        System.out.println("addUsers");
        Collection<User> users = null;
        instance.addUsers(users);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


   
    @Test
    public void testAddAlarms() throws Exception
    {
        System.out.println("addAlarms");
        Collection<Alarm> alarms = null;
        instance.addAlarms(alarms);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of updateUser method, of class JPARepository.
     */
    @Test
    public void testUpdateUser() throws Exception
    {
        System.out.println("updateUser");
        User user = null;
        instance.updateUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of updateAlarm method, of class JPARepository.
     */
    @Test
    public void testUpdateAlarm() throws Exception
    {
        System.out.println("updateAlarm");
        Alarm alarm = null;
        instance.updateAlarm(alarm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        Collection result = instance.getAllAlarms();
        assertEquals(0, result.size());
        
    }


    /**
     * Test of deleteUsers method, of class JPARepository.
     */
    @Test
    public void testDeleteUsers() throws Exception
    {
        System.out.println("deleteUsers");
        Collection<User> users = null;
        instance.deleteUsers(users);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of deleteAlarms method, of class JPARepository.
     */
    @Test
    public void testDeleteAlarms() throws Exception
    {
        System.out.println("deleteAlarms");
        Collection<Alarm> alarms = null;
        instance.deleteAlarms(alarms);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}