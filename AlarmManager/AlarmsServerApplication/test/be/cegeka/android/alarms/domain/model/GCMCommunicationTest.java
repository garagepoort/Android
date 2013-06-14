package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GCMCommunicationTest
{
    @Mock
    private Service serviceMock;
    private User user;
    private GCMCommunication gcmCommunication;

    @Before
    public void setUp()
    {
        user = new User(1, "naam", "achternaam", "paswoord", "email", false,"");
        gcmCommunication = new GCMCommunication(serviceMock);
    }
    

    @Test
    public void testRegisterUser() throws DatabaseException, BusinessException
    {
        when(serviceMock.updateUser(user)).thenReturn(user);
        
        gcmCommunication.registerUser(user, "testRegistrationId");
        
        verify(serviceMock).updateUser(user);
        assertEquals("testRegistrationId", user.getGCMid());
    }
    
    
    @Test
    public void testUnregisterUser() throws DatabaseException, BusinessException
    {
        when(serviceMock.updateUser(user)).thenReturn(user);
        
        gcmCommunication.unregisterUser(user);
        
        verify(serviceMock).updateUser(user);
        assertEquals(null, user.getGCMid());
        
    }
    
    
    @Test
    public void testNotifyUserOfChange()
    {
        /**
         * @todo Hot to test?
         */
    }
}