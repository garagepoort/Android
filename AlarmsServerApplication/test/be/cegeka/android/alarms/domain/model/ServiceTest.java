package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ServiceTest
{
    @Mock
    private GCMCommunication gcmCommunication;
    private Service service;
    
    
    @Before
    public void setUp()
    {
        service = new Service();
        service.setGCMCommunication(gcmCommunication);
    }
    

    @Test
    public void whenAlarmIsAddedToUser_ThenGCMCommunicationIsNotified() throws DatabaseException, BusinessException
    {
        User user = new User();
        user.setEmail("blur@blur.com");
        service.addAlarmUserRelation(new Alarm(), user);
        
        verify(gcmCommunication).notifyUserOfChange(user);
    }
}