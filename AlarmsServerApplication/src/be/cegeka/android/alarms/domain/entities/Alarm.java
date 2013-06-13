package be.cegeka.android.alarms.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Alarm implements Serializable
{
    @Id
    @GeneratedValue
    private Integer alarmid;
    private String title;
    private String info;
    private long dateInMillis;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<User> users = new ArrayList<>();


    public Alarm()
    {
    }


    public Alarm(Integer alarmid, String title, String info, long dateInMillis)
    {
        this.alarmid = alarmid;
        this.title = title;
        this.info = info;
        this.dateInMillis = dateInMillis;
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
    
    public void addUser(User u){
        users.add(u);
    }
    
     public void removeUser(User user)
    {
        users.remove(user);
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public void setInfo(String info)
    {
        this.info = info;
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
    
    @PreRemove
    public void detachUsers(){
        for(User u : users){
            u.removeAlarm(this);
        }
    }
    
    @PrePersist
    public void attachUsers(){
        for(User u : users){
            u.addAlarm(this);
        }
    }
}


