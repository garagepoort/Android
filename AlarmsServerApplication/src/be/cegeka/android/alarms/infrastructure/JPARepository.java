package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class JPARepository implements Repository
{
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("AlarmsServerPU");
    private EntityManager entityManager = factory.createEntityManager();


    @Override
    public User getUser(String emailadres)
    {
        User returnUser = null;
        TypedQuery query = entityManager.createNamedQuery("User.findByEmail", User.class).setParameter("email", emailadres);
        List<User> results = query.getResultList();

        if (!results.isEmpty())
        {
            returnUser = results.get(0);
        }

        return returnUser;
    }


    @Override
    public Collection<User> getAllUsers()
    {
        Query query = entityManager.createQuery("SELECT * FROM user");
        return query.getResultList();
    }


    @Override
    public Collection<Alarm> getAllAlarms()
    {
        Query query = entityManager.createQuery("SELECT a FROM Alarm a");
        return query.getResultList();

    }


    @Override
    public Collection<User> getUsersForAlarm(Alarm alarm)
    {
        Query query = entityManager.createQuery("");
        return query.getResultList();
    }


    @Override
    public Collection<Alarm> getAlarmsForUser(User user)
    {
        Query query = entityManager.createQuery("");
        return query.getResultList();

    }


    @Override
    public User addUser(User user) throws DatabaseException
    {
        User returnUser = null;

        entityManager.getTransaction().begin();
        returnUser = entityManager.merge(user);
        entityManager.getTransaction().commit();

        return returnUser;
    }


    @Override
    public Alarm addAlarm(Alarm alarm) throws DatabaseException
    {
        Alarm returnAlarm = null;
        entityManager.getTransaction().begin();
        returnAlarm = entityManager.merge(alarm);
        entityManager.getTransaction().commit();
        return returnAlarm;
    }


    @Override
    public void addUsers(Collection<User> users) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void addAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void updateUser(User user) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void updateAlarm(Alarm alarm) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void deleteUser(User user) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }


    @Override
    public void deleteAlarm(Alarm alarm) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        entityManager.remove(alarm);
        entityManager.getTransaction().commit();
    }


    @Override
    public void deleteUsers(Collection<User> users) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void deleteAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


