package be.cegeka.android.alarms.domain.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "\"user\"")
public class User implements Serializable
{
    @GeneratedValue
    @Id
    private Integer userid;
    private String naam;
    private String achternaam;
    private String paswoord;
    private String email;
    private String salt;
    private Boolean admin;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Alarm> alarms;


    public User()
    {
    }


    public User(String naam, String achternaam, String paswoord, String email, Boolean admin)
    {
        this.naam = naam;
        this.achternaam = achternaam;
        this.paswoord = paswoord;
        this.email = email;
        this.admin = admin;
    }


    public Integer getUserid()
    {
        return userid;
    }


    public String getNaam()
    {
        return naam;
    }


    public String getAchternaam()
    {
        return achternaam;
    }


    public String getPaswoord()
    {
        return paswoord;
    }


    public String getEmail()
    {
        return email;
    }


    public String getSalt()
    {
        return salt;
    }


    public Boolean getAdmin()
    {
        return admin;
    }


    public List<Alarm> getAlarms()
    {
        return Collections.unmodifiableList(alarms);
    }


    public void setNaam(String naam)
    {
        this.naam = naam;
    }


    public void setAchternaam(String achternaam)
    {
        this.achternaam = achternaam;
    }


    public void setPaswoord(String paswoord)
    {
        this.paswoord = paswoord;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }


    public void setSalt(String salt)
    {
        this.salt = salt;
    }


    public void setAdmin(Boolean admin)
    {
        this.admin = admin;
    }
    
    
    
}

