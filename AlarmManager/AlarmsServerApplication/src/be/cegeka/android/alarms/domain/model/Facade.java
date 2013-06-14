package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.exceptions.RepositoryException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Facade {

    public static final String NULL_ERROR_MESSAGE = "Argument is null";
    private Service service;
    private TransferObjectMapper transferObjectMapper;

    public Facade() {
        service = new Service();
        transferObjectMapper = new TransferObjectMapper();
    }

    public UserTO getUser(String emailadres) throws BusinessException {
        User user = service.getUser(emailadres);

        if (user == null) {
            return null;
        }

        UserTO userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }

    public Collection<UserTO> getAllUsers() throws BusinessException {
        Collection<UserTO> userTOs = new ArrayList<>();
        for (User u : service.getAllUsers()) {
            UserTO userTO = transferObjectMapper.convertUserToUserTO(u);
            userTOs.add(userTO);
        }
        return userTOs;
    }

    public Collection<AlarmTO> getAllAlarms() throws BusinessException {
        Collection<AlarmTO> alarmTOs = new ArrayList<>();
        for (Alarm a : service.getAllAlarms()) {
            alarmTOs.add(transferObjectMapper.convertAlarmToAlarmTO(a));
        }
        return alarmTOs;
    }

    public Collection<UserTO> getUsersForAlarm(AlarmTO alarmTO) throws BusinessException {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        Collection<User> usersForAlarm = service.getUsersForAlarm(alarm);

        Collection<UserTO> userTOsForAlarm = new ArrayList<>();
        for (User user : usersForAlarm) {
            UserTO userTO = transferObjectMapper.convertUserToUserTO(user);
            userTOsForAlarm.add(userTO);
        }

        return userTOsForAlarm;
    }

    public Collection<AlarmTO> getAlarmsForUser(UserTO userTO) throws BusinessException {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        Collection<Alarm> alarmsForUser = service.getAlarmsForUser(user);

        Collection<AlarmTO> alarmTOsForUser = new ArrayList<>();
        for (Alarm alarm : alarmsForUser) {
            AlarmTO alarmTO = transferObjectMapper.convertAlarmToAlarmTO(alarm);
            alarmTOsForUser.add(alarmTO);
        }

        return alarmTOsForUser;
    }

    public UserTO addUser(UserTO user) throws BusinessException {
        return transferObjectMapper.convertUserToUserTO(service.addUser(transferObjectMapper.convertUserTOToUser(user)));
    }

    public AlarmTO addAlarm(AlarmTO alarm) throws BusinessException {

        Alarm createdAlarm = service.addAlarm(transferObjectMapper.convertAlarmTOToAlarm(alarm));
        return convertToDomain(createdAlarm);

    }

    public void addUsers(Collection<UserTO> userTOs) throws BusinessException {
        Collection<User> users = new ArrayList<>();
        for (UserTO userTO : userTOs) {
            User user = transferObjectMapper.convertUserTOToUser(userTO);
            users.add(user);
        }

        service.addUsers(users);

    }

    public void addAlarms(Collection<AlarmTO> alarmTOs) throws BusinessException {
        Collection<Alarm> alarms = new ArrayList<>();
        for (AlarmTO alarmTO : alarmTOs) {
            Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
            alarms.add(alarm);
        }
        service.addAlarms(alarms);
    }

    public UserTO updateUser(UserTO userTO) throws BusinessException {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        user = service.updateUser(user);
        userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }

    public AlarmTO updateAlarm(AlarmTO alarmTO) throws BusinessException {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        alarm = service.updateAlarm(alarm);
        alarmTO = transferObjectMapper.convertAlarmToAlarmTO(alarm);

        return alarmTO;
    }

    public void deleteUser(UserTO userTO) throws BusinessException {
        User user = transferObjectMapper.convertUserTOToUser(userTO);
        service.deleteUser(user);
    }

    public void deleteAlarm(AlarmTO alarmTO) throws BusinessException {
        Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        service.deleteAlarm(alarm);
    }

    public void deleteUsers(Collection<UserTO> userTOs) throws BusinessException {
        Collection<User> users = new ArrayList<>();
        for (UserTO userTO : userTOs) {
            User user = transferObjectMapper.convertUserTOToUser(userTO);
            users.add(user);
        }
        service.deleteUsers(users);
    }

    public void deleteAlarms(Collection<AlarmTO> alarmTOs) throws BusinessException {
        Collection<Alarm> alarms = new ArrayList<>();
        for (AlarmTO alarmTO : alarmTOs) {
            Alarm alarm = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
            alarms.add(alarm);
        }
        service.deleteAlarms(alarms);
    }

    public AlarmTO getAlarm(Integer id) throws BusinessException {
        return transferObjectMapper.convertAlarmToAlarmTO(service.getAlarm(id));
    }

    public void addUserAlarmRelation(AlarmTO alarmTO, UserTO userTO) throws BusinessException {
        if (alarmTO == null || userTO == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }

        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        User user = service.getUser(userTO.getEmail());

        service.addAlarmUserRelation(alarm, user);
    }

    public UserTO getUserById(int id) throws BusinessException {
        User user = service.getUserById(id);
        UserTO userTO = transferObjectMapper.convertUserToUserTO(user);

        return userTO;
    }

    private Alarm convertToTO(AlarmTO alarm) throws BusinessException {
        return transferObjectMapper.convertAlarmTOToAlarm(alarm);
    }

    private AlarmTO convertToDomain(final Alarm createdAlarm) throws BusinessException {
        return transferObjectMapper.convertAlarmToAlarmTO(createdAlarm);
    }

    public void removeUserAlarmRelation(AlarmTO alarmTO, UserTO userTO) throws BusinessException {
        if (alarmTO == null || userTO == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        Alarm alarm = service.getAlarm(alarmTO.getAlarmID());
        User user = service.getUser(userTO.getEmail());
        service.removeAlarmUserRelation(alarm, user);
    }

    public void closeDatabase() {
        service.closeDatabase();
    }

    public UserTO upgradeUser(UserTO userTO) throws DatabaseException, BusinessException {
        User user = service.getUser(userTO.getEmail());
        return transferObjectMapper.convertUserToUserTO(service.upgradeUser(user));
    }

    public UserTO downgradeUser(UserTO userTO) throws BusinessException, DatabaseException {
        User user = service.getUser(userTO.getEmail());
        return transferObjectMapper.convertUserToUserTO(service.downgradeUser(user));
    }
    
    

    /**
     * ONLY FOR TESTING.
     *
     * @param service
     */
    void setService(Service service) {
        this.service = service;
    }

    /**
     * ONLY FOR TESTING.
     *
     * @param transferObjectMapper
     */
    void setTransferObjectMapper(TransferObjectMapper transferObjectMapper) {
        this.transferObjectMapper = transferObjectMapper;
    }

    public boolean authenticateUser(UserTO userTO, String paswoord) throws BusinessException {
        if (userTO == null) {
            return false;
        }
        User user = service.getUserById(userTO.getUserid());
        if (user == null) {
            return false;
        }
        return service.authenticate(user, paswoord);
    }

    public boolean registerUser(String email, String GCMID) throws BusinessException {
        try {
            return service.registerUser(email,GCMID);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void unregisterUser(String registrationID) throws RepositoryException {
        service.unregisterUser(registrationID);
    }

   
}
