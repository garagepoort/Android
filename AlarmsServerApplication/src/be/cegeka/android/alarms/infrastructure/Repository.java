package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import java.util.Collection;



public interface Repository
{
    public User getUser(String emailadres);
    public Alarm getAlarm(Integer id);
    public Collection<User> getAllUsers();
    public Collection<Alarm> getAllAlarms();
    public Collection<User> getUsersForAlarm(Alarm alarm);
    public Collection<Alarm> getAlarmsForUser(User user);
    
    public User addUser(User user) throws DatabaseException;
    public Alarm addAlarm(Alarm alarm) throws DatabaseException;
    public void addUsers(Collection<User> users) throws DatabaseException;
    public void addAlarms(Collection<Alarm> alarms) throws DatabaseException;

    public User updateUser(User user) throws DatabaseException;
    public Alarm updateAlarm(Alarm alarm) throws DatabaseException;
    
    public void deleteUser(User user) throws DatabaseException;
    public void deleteAlarm(Alarm alarm) throws DatabaseException;
    public void deleteUsers(Collection<User> users) throws DatabaseException;
    public void deleteAlarms(Collection<Alarm> alarms) throws DatabaseException;
}
