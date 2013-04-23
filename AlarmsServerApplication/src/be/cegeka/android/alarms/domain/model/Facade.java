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


public class Facade
{
    public static final String NULL_ERROR_MESSAGE = "Argument is null";
    private Service service;
    private TransferObjectMapper transferObjectMapper;


    public Facade()
    {
        service = new Service();
        transferObjectMapper = new TransferObjectMapper();
    }


    public UserTO getUser(String emailadres) throws BusinessException
    {
        return TransferObjectMapper.convertUserToUserTO(service.getUser(emailadres));
    }


    public Collection<UserTO> getAllUsers() throws BusinessException
    {
        Collection<UserTO> userTOs = new ArrayList<>();
        for (User u : service.getAllUsers())
        {
            userTOs.add(TransferObjectMapper.convertUserToUserTO(u));
        }
        return userTOs;
    }


    public Collection<AlarmTO> getAllAlarms() throws BusinessException
    {
        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        for(Alarm a : service.getAllAlarms()){
            alarmTOs.add(transferObjectMapper.convertAlarmToAlarmTO(a));
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
            return TransferObjectMapper.convertUserToUserTO(service.addUser(TransferObjectMapper.convertUserTOToUser(user)));
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
            final Alarm createdAlarm = service.addAlarm(convertToTO(alarm));
            return convertToDomain(createdAlarm);
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
        return transferObjectMapper.convertAlarmToAlarmTO(service.getAlarm(id));
    }
    
    public void addAlarmToUser(AlarmTO alarmTO, UserTO userTO) throws BusinessException{
        if(alarmTO == null || userTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        
        try
        {
            Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
            User user = service.getUser(userTO.getEmail());
            user.addAlarm(alarm);
            service.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    void setService(Service service)
    {
        this.service = service;
    }


    void setTransferObjectMapper(TransferObjectMapper transferObjectMapper)
    {
        this.transferObjectMapper = transferObjectMapper;
    }


    private Alarm convertToTO(AlarmTO alarm) throws BusinessException
    {
        return transferObjectMapper.convertAlarmTOToAlarm(alarm);
    }


    private AlarmTO convertToDomain(final Alarm createdAlarm) throws BusinessException
    {
        return transferObjectMapper.convertAlarmToAlarmTO(createdAlarm);
    }
}


