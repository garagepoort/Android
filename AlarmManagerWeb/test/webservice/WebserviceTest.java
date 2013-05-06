/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.domain.model.Facade;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.Calendar;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebserviceTest {

    private AlarmManagerWebservice service;
    private UserTO user;
    private AlarmTO alarm;
    private AlarmTO alarm2;
    @Mock
    private Facade facade;
    private ArrayList<AlarmTO> alarms;

    @Before
    public void setUp() throws DatabaseException, BusinessException {
        facade = Mockito.mock(Facade.class);
        user = new UserTO(1, "testUser", "test", "user", "testpaswoord", Boolean.TRUE);

        alarm = new AlarmTO(2, "testAlarm", "testInfo", Calendar.getInstance().getTimeInMillis());
        alarm2 = new AlarmTO(3, "testAlarm 2", "testInfo 2", Calendar.getInstance().getTimeInMillis());
        
        alarms = new ArrayList<AlarmTO>();
        alarms.add(alarm);
        alarms.add(alarm2);
        Mockito.when(facade.getAlarm(1)).thenReturn(alarm);
        Mockito.when(facade.getAlarm(2)).thenReturn(alarm2);
        Mockito.when(facade.getUser("user")).thenReturn(user);
        Mockito.when(facade.authenticateUser(user, "testpaswoord")).thenReturn(true);
        Mockito.when(facade.getAlarmsForUser(user)).thenReturn(alarms);
        service = new AlarmManagerWebservice();
        service.setFacade(facade);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLoginCorrect() throws BusinessException {
        UserTO userTO = service.login("user", "testpaswoord").getUserTO();
        Assert.assertNotNull(userTO);
    }

    @Test
    public void testLoginIncorrect() throws BusinessException {
        UserTO user2 = new UserTO(5, "user2", "user2achternaam", "pas2", false,"fezefuifhezuifhze");
        Mockito.when(facade.getUser("user2")).thenReturn(user2);
        Mockito.when(facade.authenticateUser(user2, "pas2")).thenReturn(true);
        UserTO userTO2 = service.login("user2", "dza").getUserTO();
        assertNull(userTO2);
    }
    
    @Test
    public void testGetAllAlarmsCorrect() throws BusinessException{
        //ArrayList<AlarmTO> alarms = service.getAllAlarmsFromUser("user");
        /**
         * @todo
         * Implement again
         */
        assertEquals(alarms, this.alarms);
    }
    
    @Test
    public void testGetAllAlarmsUserDoesNotExists() throws BusinessException{
        Mockito.when(facade.getUser("nonExistingUser")).thenThrow(new BusinessException("User does not exist"));
        //ArrayList<AlarmTO> alarms = service.getAllAlarmsFromUser("nonExistingUser");
        assertNull(alarms);
    }
    
    @Test
    public void testGetAllAlarmsEmptyList() throws BusinessException{
        //ArrayList<AlarmTO> alarms = service.getAllAlarmsFromUser("user2");
        Assert.assertNotNull(alarms);
        Assert.assertTrue(alarms.isEmpty());
    }
    
//    @Test(expected = BusinessException.class)
//    public void testRegisterUserWrongUser() throws BusinessException {
//        Mockito.doThrow(BusinessException.class).when(facade).registerUser(Mockito.any(String.class), Mockito.any(String.class));
//        service.registerUser("", "");
//    }
}