package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.infrastructure.JPARepository;
import be.cegeka.android.alarms.infrastructure.Repository;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.Collection;


public class AlarmService
{
    public static final String NULL_ERROR_MESSAGE = "Argument is null";
    private Repository repository;


    public AlarmService()
    {
        repository = new JPARepository();
    }


    public UserTO getUser(String emailadres) throws BusinessException
    {
        return TransferObjectMapper.convertUserToUserTO(repository.getUser(emailadres));
    }


    public Collection<UserTO> getAllUsers() throws BusinessException
    {
        Collection<UserTO> userTOs = new ArrayList<>();
        for (User u : repository.getAllUsers())
        {
            userTOs.add(TransferObjectMapper.convertUserToUserTO(u));
        }
        return userTOs;
    }


    public Collection<AlarmTO> getAllAlarms() throws BusinessException
    {
        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        for(Alarm a : repository.getAllAlarms()){
            alarmTOs.add(TransferObjectMapper.convertAlarmToAlarmTO(a));
        }
        return alarmTOs;
    }


    public Collection<UserTO> getUsersForAlarm(AlarmTO alarm)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Collection<AlarmTO> getAlarmsForUser(UserTO user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public UserTO addUser(UserTO user) throws BusinessException
    {
        try
        {
            return TransferObjectMapper.convertUserToUserTO(repository.addUser(TransferObjectMapper.convertUserTOToUser(user)));
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public AlarmTO addAlarm(AlarmTO alarm) throws BusinessException
    {
        try
        {
            return TransferObjectMapper.convertAlarmToAlarmTO(repository.addAlarm(TransferObjectMapper.convertAlarmTOToAlarm(alarm)));
        }
        catch(DatabaseException e){
            throw new BusinessException(e);
        }
    }


    public void addUsers(Collection<UserTO> users) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void addAlarms(Collection<AlarmTO> alarms) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public UserTO updateUser(UserTO user) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public AlarmTO updateAlarm(AlarmTO alarm) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void deleteUser(UserTO user) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void deleteAlarm(AlarmTO alarm) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void deleteUsers(Collection<UserTO> users) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void deleteAlarms(Collection<AlarmTO> alarms) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public AlarmTO getAlarm(Integer id) throws BusinessException{
        return TransferObjectMapper.convertAlarmToAlarmTO(repository.getAlarm(id));
    }
    
    public void addAlarmToUser(AlarmTO alarmTO, UserTO userTO) throws BusinessException{
        if(alarmTO == null || userTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        
        try
        {
            Alarm alarm = repository.getAlarm(alarmTO.getAlarmID());
            User user = repository.getUser(userTO.getEmail());
            user.addAlarm(alarm);
            repository.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }
}


