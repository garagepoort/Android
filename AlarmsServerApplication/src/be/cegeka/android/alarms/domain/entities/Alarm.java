package be.cegeka.android.alarms.domain.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Alarm implements Serializable
{
    @Id
    @GeneratedValue
    private Integer alarmid;
    private String title;
    private String info;
    private long dateInMillis;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;


    public Alarm()
    {
    }


    public Alarm(String title, String info, long dateInMillis)
    {
        this.title = title;
        this.info = info;
        this.dateInMillis = dateInMillis;
    }


    public Integer getAlarmid()
    {
        return alarmid;
    }


    public String getTitle()
    {
        return title;
    }


    public String getInfo()
    {
        return info;
    }


    public long getDateInMillis()
    {
        return dateInMillis;
    }


    public List<User> getUsers()
    {
        return Collections.unmodifiableList(users);
    }


    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.alarmid);
        return hash;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Alarm other = (Alarm) obj;
        if (!Objects.equals(this.alarmid, other.alarmid))
        {
            return false;
        }
        return true;
    }
    
    
}


