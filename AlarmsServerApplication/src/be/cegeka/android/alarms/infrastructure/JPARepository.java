package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.exceptions.RepositoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPARepository implements Repository {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("AlarmsServerApplicationPU");
    private EntityManager entityManager = factory.createEntityManager();
    private static JPARepository instance = new JPARepository();

    private JPARepository() {
    }

    public static JPARepository getInstance() {
        return instance;
    }

    @Override
    public User getUser(String emailadres) {
        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.email = ?1", User.class);
        q.setParameter(1, emailadres);
        List<User> users = q.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public Collection<User> getAllUsers() {
        Query query = entityManager.createQuery("SELECT U FROM User u");
        return query.getResultList();
    }

    @Override
    public Collection<Alarm> getAllAlarms() {
        Query query = entityManager.createQuery("SELECT a FROM Alarm a");
        return query.getResultList();

    }

    @Override
    public Collection<User> getUsersForAlarm(Alarm alarm) throws RepositoryException {
        alarm = getAlarm(alarm.getAlarmid());
        if (alarm == null) {
            throw new RepositoryException("Alarm does not exist.");
        }
        if (alarm.getUsers() == null) {
            return new ArrayList<>();
        }
        return alarm.getUsers();
    }

    @Override
    public Collection<Alarm> getAlarmsForUser(User user) throws RepositoryException {
        user = getUser(user.getEmail());
        if (user == null) {
            throw new RepositoryException("User does not exists");
        }
        if (user.getAlarms() == null) {
            return new ArrayList<>();
        }
        return user.getAlarms();
    }

    @Override
    public User addUser(User user) throws RepositoryException {
        beginTransaction();
        // This is called because the constructor doesn't use the setPaswoord
        // method and you need to call that method because it generates the salt en hashes the password.
        user.setPaswoord(user.getPaswoord());
        entityManager.persist(user);
        entityManager.flush();
        commitTransaction();
        return user;
    }

    @Override
    public Alarm addAlarm(Alarm alarm) throws RepositoryException {
        beginTransaction();
        entityManager.persist(alarm);
        entityManager.flush();
        commitTransaction();

        return alarm;
    }

    @Override
    public void addUsers(Collection<User> users) throws RepositoryException {
        for (User u : users) {
            addUser(u);
        }
    }

    @Override
    public void addAlarms(Collection<Alarm> alarms) throws RepositoryException {
        for (Alarm a : alarms) {
            addAlarm(a);
        }
    }

    @Override
    public User updateUser(User user) throws RepositoryException {
        beginTransaction();
        user = entityManager.merge(user);
        commitTransaction();
        return user;
    }

    @Override
    public Alarm updateAlarm(Alarm alarm) throws RepositoryException {
        beginTransaction();
        alarm = entityManager.merge(alarm);
        commitTransaction();
        return alarm;
    }

    @Override
    public void deleteUser(User user) throws RepositoryException {
        beginTransaction();
        user = getUserById(user.getUserid());
        entityManager.refresh(user);
        entityManager.remove(user);
        commitTransaction();
    }

    @Override
    public void deleteAlarm(Alarm alarm) throws RepositoryException {
        beginTransaction();
        alarm = getAlarm(alarm.getAlarmid());
        entityManager.refresh(alarm);
        entityManager.remove(alarm);
        commitTransaction();
    }

    @Override
    public void deleteUsers(Collection<User> users) throws RepositoryException {
        for (User u : users) {
            deleteUser(u);
        }
    }

    @Override
    public void deleteAlarms(Collection<Alarm> alarms) throws RepositoryException {
        for (Alarm a : alarms) {
            deleteAlarm(a);
        }
    }

    @Override
    public Alarm getAlarm(Integer alarmid) {
        return entityManager.find(Alarm.class, alarmid);
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    private void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    private void commitTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void closeDatabase() {
        entityManager.close();
        factory.close();
    }

    public boolean authenticateUser(User user, String paswoord) {
        return user.authenticate(paswoord);
    }

    @Override
    public User upgradeUser(User user) throws RepositoryException {
        beginTransaction();
        user = entityManager.merge(user);
        user.setAdmin(Boolean.TRUE);
        updateUser(user);
        commitTransaction();
        return user;
    }

    @Override
    public User downgradeUser(User user) throws RepositoryException {
        beginTransaction();
        user = entityManager.merge(user);
        user.setAdmin(Boolean.FALSE);
        updateUser(user);
        commitTransaction();
        return user;
    }

    @Override
    public void addUserAlarmRelation(User user, Alarm alarm) throws RepositoryException {
        beginTransaction();
        user.addAlarm(alarm);
        alarm.addUser(user);
        entityManager.merge(alarm);
        entityManager.merge(user);
        commitTransaction();
    }

    @Override
    public void removeUserAlarmRelation(User user, Alarm alarm) throws RepositoryException {
        beginTransaction();
        user.removeAlarm(alarm);
        alarm.removeUser(user);
        entityManager.merge(alarm);
        entityManager.merge(user);
        commitTransaction();
    }

    @Override
    public User refreshUser(User user) throws RepositoryException {
        entityManager.refresh(user);
        return user;
    }

    @Override
    public Alarm refreshAlarm(Alarm alarm) throws RepositoryException {
        entityManager.refresh(alarm);
        return alarm;
    }

    @Override
    public void registerUser(String email, String GCMID) throws RepositoryException {
        if(email == null){
            throw new RepositoryException("E-mail can't be null.");
        }
        if(GCMID == null){
            throw new RepositoryException("Google Cloud Messaging ID can't be null!");
        }
        User user = getUser(email);
        if(user == null){
            throw new RepositoryException("User doesn't exist.");
        }
        user.setGCMid(GCMID);
        updateUser(user);
    }
}
