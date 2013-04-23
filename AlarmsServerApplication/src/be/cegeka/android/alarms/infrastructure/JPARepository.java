package be.cegeka.android.alarms.infrastructure;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.User;
import java.util.ArrayList;
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
    public EntityManager entityManager = factory.createEntityManager();


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
        Query query = entityManager.createQuery("SELECT U FROM User u");
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
        ArrayList<User> users = new ArrayList<>(getAllUsers());
        ArrayList<User> returnUsers = new ArrayList<>();
        for (User u : users)
        {
            if (u.getAlarms().contains(alarm))
            {
                returnUsers.add(u);
            }
        }
        return returnUsers;
    }


    @Override
    public Collection<Alarm> getAlarmsForUser(User user)
    {
        ArrayList<Alarm> alarms = new ArrayList<>(getAllAlarms());
        ArrayList<Alarm> returnAlarms = new ArrayList<>();
        for (Alarm a : alarms)
        {
            if (a.getUsers().contains(user))
            {
                returnAlarms.add(a);
            }
        }
        return returnAlarms;

    }


    @Override
    public User addUser(User user) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        for (Alarm a : user.getAlarms())
        {
            a.addUser(user);
        }
        entityManager.getTransaction().commit();
        return user;
    }


    @Override
    public Alarm addAlarm(Alarm alarm) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        entityManager.persist(alarm);
        entityManager.flush();
        for (User u : alarm.getUsers())
        {
            u.addAlarm(alarm);
        }
        entityManager.getTransaction().commit();

        return alarm;
    }


    @Override
    public void addUsers(Collection<User> users) throws DatabaseException
    {
        for (User u : users)
        {
            addUser(u);
        }
    }


    @Override
    public void addAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        for (Alarm a : alarms)
        {
            addAlarm(a);
        }
    }


    @Override
    public User updateUser(User user) throws DatabaseException
    {
        User returnUser = null;
        entityManager.getTransaction().begin();
        for (Alarm a : user.getAlarms())
        {
            a.removeUser(user);
        }
        returnUser = entityManager.merge(user);
        entityManager.getTransaction().commit();
        return returnUser;
    }


    @Override
    public Alarm updateAlarm(Alarm alarm) throws DatabaseException
    {
        Alarm returnAlarm = null;
        entityManager.getTransaction().begin();
        for (User u : alarm.getUsers())
        {
            u.removeAlarm(alarm);
        }
        returnAlarm = entityManager.merge(alarm);
        entityManager.getTransaction().commit();
        return returnAlarm;
    }


    @Override
    public void deleteUser(User user) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        for (Alarm a : user.getAlarms())
        {
            a.removeUser(user);
        }
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }


    @Override
    public void deleteAlarm(Alarm alarm) throws DatabaseException
    {
        entityManager.getTransaction().begin();
        for (User u : alarm.getUsers())
        {
            u.removeAlarm(alarm);
        }
        entityManager.remove(alarm);
        entityManager.getTransaction().commit();
    }


    @Override
    public void deleteUsers(Collection<User> users) throws DatabaseException
    {
        for (User u : users)
        {
            deleteUser(u);
        }
    }


    @Override
    public void deleteAlarms(Collection<Alarm> alarms) throws DatabaseException
    {
        for (Alarm a : alarms)
        {
            deleteAlarm(a);
        }
    }

    @Override
    public Alarm getAlarm(Integer alarmid)
    {
        return entityManager.find(Alarm.class, alarmid);
    }


    @Override
    public User getUserById(int id)
    {
        User returnUser = null;
        TypedQuery query = entityManager.createNamedQuery("User.findById", User.class).setParameter("id", id);
        List<User> results = query.getResultList();
        
        if (!results.isEmpty())
        {
            returnUser = results.get(0);
        }
        
        return returnUser;
    }


    @Override
    public void removeUserFromAlarm(User user, Alarm alarm) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void removeAlarmFromUser(Alarm alarm, User user) throws DatabaseException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


