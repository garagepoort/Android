package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.exceptions.RepositoryException;
import java.util.Collection;



public interface Repository
{
    public User getUser(String emailadres);
    public User getUserById(int id);
    public Alarm getAlarm(Integer id);
    public Collection<User> getAllUsers();
    public Collection<Alarm> getAllAlarms();
    public Collection<User> getUsersForAlarm(Alarm alarm) throws RepositoryException;
    public Collection<Alarm> getAlarmsForUser(User user) throws RepositoryException;
    
    public User addUser(User user) throws RepositoryException;
    public Alarm addAlarm(Alarm alarm) throws RepositoryException;
    public void addUsers(Collection<User> users) throws RepositoryException;
    public void addAlarms(Collection<Alarm> alarms) throws RepositoryException;

    public User updateUser(User user) throws RepositoryException;
    public Alarm updateAlarm(Alarm alarm) throws RepositoryException;
    
    public void deleteUser(User user) throws RepositoryException;
    public void deleteAlarm(Alarm alarm) throws RepositoryException;
    public void deleteUsers(Collection<User> users) throws RepositoryException;
    public void deleteAlarms(Collection<Alarm> alarms) throws RepositoryException;
    
    public void addUserAlarmRelation(User user, Alarm alarm) throws RepositoryException;
    public void removeUserAlarmRelation(User user, Alarm alarm) throws RepositoryException;
    
    public User upgradeUser(User user) throws RepositoryException;
    public User downgradeUser(User user) throws RepositoryException;
    
    public User refreshUser(User user) throws RepositoryException;
    public Alarm refreshAlarm(Alarm alarm) throws RepositoryException;
    
    public boolean authenticateUser(User user, String paswoord) throws RepositoryException;
    public void closeDatabase();

    public void registerUser(String email, String GCMID) throws RepositoryException;
}