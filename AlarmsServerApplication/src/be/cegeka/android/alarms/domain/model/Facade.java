package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        User user = service.getUser(emailadres);
        
        if(user == null){
            return null;
        }

        UserTO userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }


    public Collection<UserTO> getAllUsers() throws BusinessException
    {
        Collection<UserTO> userTOs = new ArrayList<>();
        for (User u : service.getAllUsers())
        {
            UserTO userTO = transferObjectMapper.convertUserToUserTO(u);
            userTOs.add(userTO);
        }
        return userTOs;
    }


    public Collection<AlarmTO> getAllAlarms() throws BusinessException
    {
        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        for (Alarm a : service.getAllAlarms())
        {
            alarmTOs.add(transferObjectMapper.convertAlarmToAlarmTO(a));
        }
        return alarmTOs;
    }


    public Collection<UserTO> getUsersForAlarm(AlarmTO alarmTO) throws BusinessException
    {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        Collection<User> usersForAlarm = service.getUsersForAlarm(alarm);

        Collection<UserTO> userTOsForAlarm = new ArrayList<>();
        for (User user : usersForAlarm)
        {
            UserTO userTO = transferObjectMapper.convertUserToUserTO(user);
            userTOsForAlarm.add(userTO);
        }

        return userTOsForAlarm;
    }


    public Collection<AlarmTO> getAlarmsForUser(UserTO userTO) throws BusinessException
    {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        Collection<Alarm> alarmsForUser = service.getAlarmsForUser(user);

        Collection<AlarmTO> alarmTOsForUser = new ArrayList<>();
        for (Alarm alarm : alarmsForUser)
        {
            AlarmTO alarmTO = transferObjectMapper.convertAlarmToAlarmTO(alarm);
            alarmTOsForUser.add(alarmTO);
        }

        return alarmTOsForUser;
    }


    public UserTO addUser(UserTO user) throws BusinessException
    {
        try
        {
            return transferObjectMapper.convertUserToUserTO(service.addUser(transferObjectMapper.convertUserTOToUser(user)));
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
        catch (DatabaseException e)
        {
            throw new BusinessException(e);
        }
    }


    public void addUsers(Collection<UserTO> userTOs) throws BusinessException
    {
        Collection<User> users = new ArrayList<>();
        for (UserTO userTO : userTOs)
        {
            User user = transferObjectMapper.convertUserTOToUser(userTO);
            users.add(user);
        }
        try
        {
            service.addUsers(users);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void addAlarms(Collection<AlarmTO> alarmTOs) throws BusinessException
    {
        Collection<Alarm> alarms = new ArrayList<>();
        for (AlarmTO alarmTO : alarmTOs)
        {
            Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
            alarms.add(alarm);
        }
        try
        {
            service.addAlarms(alarms);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public UserTO updateUser(UserTO userTO) throws BusinessException
    {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        try
        {
            user = service.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
        userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }


    public AlarmTO updateAlarm(AlarmTO alarmTO) throws BusinessException
    {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        try
        {
            alarm = service.updateAlarm(alarm);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
        alarmTO = transferObjectMapper.convertAlarmToAlarmTO(alarm);

        return alarmTO;
    }


    public void deleteUser(UserTO userTO) throws BusinessException
    {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        try
        {
            service.deleteUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void deleteAlarm(AlarmTO alarmTO) throws BusinessException
    {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        try
        {
            service.deleteAlarm(alarm);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void deleteUsers(Collection<UserTO> userTOs) throws BusinessException
    {
        Collection<User> users = new ArrayList<>();
        for (UserTO userTO : userTOs)
        {
            User user = transferObjectMapper.convertUserTOToUser(userTO);
            users.add(user);
        }
        try
        {
            service.deleteUsers(users);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void deleteAlarms(Collection<AlarmTO> alarmTOs) throws BusinessException
    {
        Collection<Alarm> alarms = new ArrayList<>();
        for (AlarmTO alarmTO : alarmTOs)
        {
            Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
            alarms.add(alarm);
        }
        try
        {
            service.deleteAlarms(alarms);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public AlarmTO getAlarm(Integer id) throws BusinessException
    {
        return transferObjectMapper.convertAlarmToAlarmTO(service.getAlarm(id));
    }


    public void addUserToAlarm(UserTO userTO, AlarmTO alarmTO) throws BusinessException
    {
        if (alarmTO == null || userTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }

        User user = service.getUser(userTO.getEmail());
        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        
        try
        {
            service.addUserToAlarm(user, alarm);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void addAlarmToUser(AlarmTO alarmTO, UserTO userTO) throws BusinessException
    {
        if (alarmTO == null || userTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }

        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        User user = service.getUser(userTO.getEmail());
        
        try
        {
            service.addAlarmToUser(alarm, user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public UserTO getUserById(int id) throws BusinessException
    {
        User user = service.getUserById(id);
        UserTO userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }


    private Alarm convertToTO(AlarmTO alarm) throws BusinessException
    {
        return transferObjectMapper.convertAlarmTOToAlarm(alarm);
    }


    private AlarmTO convertToDomain(final Alarm createdAlarm) throws BusinessException
    {
        return transferObjectMapper.convertAlarmToAlarmTO(createdAlarm);
    }


    public void removeUserFromAlarm(UserTO userTO, AlarmTO alarmTO) throws BusinessException
    {
        User user = service.getUser(userTO.getEmail());
        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        
        alarm.removeUser(user);
        
        try
        {
            service.updateAlarm(alarm);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void removeAlarmFromUser(AlarmTO alarmTO, UserTO userTO) throws BusinessException
    {
        User user = service.getUser(userTO.getEmail());
        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        
        user.removeAlarm(alarm);
        
        try
        {
            service.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    /**
     * ONLY FOR TESTING.
     *
     * @param service
     */
    void setService(Service service)
    {
        this.service = service;
    }


    /**
     * ONLY FOR TESTING.
     *
     * @param transferObjectMapper
     */
    void setTransferObjectMapper(TransferObjectMapper transferObjectMapper)
    {
        this.transferObjectMapper = transferObjectMapper;
    }
    
}


