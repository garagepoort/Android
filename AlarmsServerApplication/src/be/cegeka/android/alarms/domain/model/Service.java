package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.exceptions.RepositoryException;
import be.cegeka.android.alarms.infrastructure.JPARepository;
import be.cegeka.android.alarms.infrastructure.Repository;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    private Repository repository;
    private GCMCommunication gcmCommunication;

    public Service() {
        repository = JPARepository.getInstance();
        gcmCommunication = new GCMCommunication(this);
    }

    public User getUser(String emailadres) {
        return repository.getUser(emailadres);
    }

    public Alarm getAlarm(Integer id) {
        return repository.getAlarm(id);
    }

    public Collection<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public Collection<Alarm> getAllAlarms() {
        return repository.getAllAlarms();
    }

    public Collection<User> getUsersForAlarm(Alarm alarm) throws BusinessException {
        try {
            return repository.getUsersForAlarm(alarm);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Collection<Alarm> getAlarmsForUser(User user) throws BusinessException {
        try {
            return repository.getAlarmsForUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public User addUser(User user) throws BusinessException {
        try {
            return repository.addUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Alarm addAlarm(Alarm alarm) throws BusinessException {
        try {
            return repository.addAlarm(alarm);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void addUsers(Collection<User> users) throws BusinessException {
        try {
            repository.addUsers(users);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void addAlarms(Collection<Alarm> alarms) throws BusinessException {
        try {
            repository.addAlarms(alarms);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public User updateUser(User user) throws BusinessException {
        try {
            return repository.updateUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Alarm updateAlarm(Alarm alarm) throws BusinessException {
        try {
            return repository.updateAlarm(alarm);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void deleteUser(User user) throws BusinessException {
        try {
            repository.deleteUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void deleteAlarm(Alarm alarm) throws BusinessException {
        try {
            repository.deleteAlarm(alarm);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void deleteUsers(Collection<User> users) throws BusinessException {
        try {
            repository.deleteUsers(users);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void deleteAlarms(Collection<Alarm> alarms) throws BusinessException {
        try {
            repository.deleteAlarms(alarms);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public User getUserById(int id) {
        return repository.getUserById(id);
    }

    public void closeDatabase() {
        repository.closeDatabase();
    }

    public boolean authenticate(User user, String paswoord) throws BusinessException {
        try {
            return repository.authenticateUser(user, paswoord);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public User upgradeUser(User user) throws BusinessException {
        try {
            return repository.upgradeUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public User downgradeUser(User user) throws BusinessException {
        try {
            return repository.downgradeUser(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void addAlarmUserRelation(Alarm alarm, User user) throws BusinessException {
        try {
            repository.addUserAlarmRelation(user, alarm);
            gcmCommunication.notifyUserOfChange(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void removeAlarmUserRelation(Alarm alarm, User user) throws BusinessException {
        try {
            repository.removeUserAlarmRelation(user, alarm);
            gcmCommunication.notifyUserOfChange(user);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public boolean registerUser(String email, String GCMID) throws RepositoryException {
        return repository.registerUser(email, GCMID);
    }

    /**
     * ONLY FOR TESTING.
     *
     * @param gcmCommunication
     */
    void setGCMCommunication(GCMCommunication gcmCommunication) {
        this.gcmCommunication = gcmCommunication;
    }
}
