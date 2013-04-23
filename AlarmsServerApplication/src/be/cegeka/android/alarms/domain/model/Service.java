package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import be.cegeka.android.alarms.infrastructure.JPARepository;
import be.cegeka.android.alarms.infrastructure.Repository;
import java.util.Collection;
import java.util.Collections;



public class Service
{
    private Repository repository;


    public Service()
    {
        repository = new JPARepository();
    }
    
    
    
    public User getUser(String emailadres)
    {
        return repository.getUser(emailadres);
    }


    public Alarm getAlarm(Integer id)
    {
        return repository.getAlarm(id);
    }


    public Collection<User> getAllUsers()
    {
        return repository.getAllUsers();
    }


    public Collection<Alarm> getAllAlarms()
    {
        return repository.getAllAlarms();
    }


    public Collection<User> getUsersForAlarm(Alarm alarm)
    {
        return repository.getUsersForAlarm(alarm);
    }


    public Collection<Alarm> getAlarmsForUser(User user)
    {
        return repository.getAlarmsForUser(user);
    }


    public User addUser(User user) throws DatabaseException
    {
        return repository.addUser(user);
    }


    public Alarm addAlarm(Alarm alarm) throws DatabaseException
    {
        return repository.addAlarm(alarm);
    }


    public void addUsers(Collection<User> users) throws DatabaseException
    {
        repository.addUsers(users);
    }


    public void addAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        repository.addAlarms(alarms);
    }


    public User updateUser(User user) throws DatabaseException
    {
        return repository.updateUser(user);
    }


    public Alarm updateAlarm(Alarm alarm) throws DatabaseException
    {
        return repository.updateAlarm(alarm);
    }


    public void deleteUser(User user) throws DatabaseException
    {
        repository.deleteUser(user);
    }


    public void deleteAlarm(Alarm alarm) throws DatabaseException
    {
        repository.deleteAlarm(alarm);
    }


    public void deleteUsers(Collection<User> users) throws DatabaseException
    {
        repository.deleteUsers(users);
    }


    public void deleteAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        repository.deleteAlarms(alarms);
    }

    
    public User getUserById(int id)
    {
        return repository.getUserById(id);
    }
    
    
    public void removeUserFromAlarm(User user, Alarm alarm) throws DatabaseException
    {
        repository.removeUserFromAlarm(user, alarm);
    }


    public void removeAlarmFromUser(Alarm alarm, User user) throws DatabaseException
    {
        repository.removeAlarmFromUser(alarm, user);
    }
}
