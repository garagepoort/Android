
import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class test
{
    public static void main(String[] args)
    {
        Alarm alarm = new Alarm("a", "a", 200000);
        User user = new User();
        Alarm repeatedAlarm = new RepeatedAlarm();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AlarmsServerPU");
        EntityManager em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(alarm);
        em.persist(user);
        em.persist(repeatedAlarm);
        em.getTransaction().commit();
    }
}


