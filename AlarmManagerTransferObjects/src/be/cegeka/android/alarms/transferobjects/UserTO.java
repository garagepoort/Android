package be.cegeka.android.alarms.transferobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserTO
{
    private Integer userid;
    private String naam;
    private String achternaam;
    private String paswoord;
    private String repeatPaswoord;
    private String email;
    private Boolean admin;

    public UserTO(Integer userid, String naam, String achternaam, String paswoord, String repeatPaswoord, String email, Boolean admin)
    {
        this.userid = userid;
        this.naam = naam;
        this.achternaam = achternaam;
        this.paswoord = paswoord;
        this.repeatPaswoord = repeatPaswoord;
        this.email = email;
        this.admin = admin;
    }


    public UserTO(Integer userid, String naam, String achternaam, String paswoord, String email, Boolean admin)
    {
        this.userid = userid;
        this.naam = naam;
        this.achternaam = achternaam;
        this.paswoord = paswoord;
        this.email = email;
        this.admin = admin;
    }


    public UserTO(Integer userid, String naam, String achternaam, String email, Boolean admin)
    {
        this.userid = userid;
        this.naam = naam;
        this.achternaam = achternaam;
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


    public String getEmail()
    {
        return email;
    }


    public Boolean isAdmin()
    {
        return admin;
    }


    public String getPaswoord()
    {
        return paswoord;
    }


    public void setPaswoord(String paswoord)
    {
        this.paswoord = paswoord;
    }


    public String getRepeatPaswoord()
    {
        return repeatPaswoord;
    }


    public void setRepeatPaswoord(String repeatPaswoord)
    {
        this.repeatPaswoord = repeatPaswoord;
    }


    public Boolean getAdmin()
    {
        return admin;
    }


    public void setAdmin(Boolean admin)
    {
        this.admin = admin;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
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
        final UserTO other = (UserTO) obj;
        return (other.getNaam().equals(this.getNaam()) && other.getAchternaam().equals(this.getAchternaam()) && other.getEmail().equals(this.getEmail()));
    }
    
}
