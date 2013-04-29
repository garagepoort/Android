package be.cegeka.android.alarms.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import be.cegeka.android.alarms.domain.model.PasswordEncrypter;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "\"USER\"")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    private String naam;
    private String achternaam;
    private String paswoord;
    @Column(unique = true, nullable = false)
    private String email;
    private String salt;
    private Boolean admin;
    private String GCMid;
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "users")
    private List<Alarm> alarms = new ArrayList<>();

    public User() {
    }

    public User(Integer userid, String naam, String achternaam, String paswoord, String email, Boolean admin) {
        this.userid = userid;
        this.naam = naam;
        this.achternaam = achternaam;
        this.paswoord = paswoord;
        this.email = email;
        this.admin = admin;
    }

    public User(String naam, String achternaam, String paswoord, String email, Boolean admin) {
        this.naam = naam;
        this.achternaam = achternaam;
        this.paswoord = paswoord;
        this.email = email;
        this.admin = admin;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNaam() {
        return naam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getPaswoord() {
        return paswoord;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public List<Alarm> getAlarms() {
        return Collections.unmodifiableList(alarms);
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setPaswoord(String paswoord) {
        PasswordEncrypter.EncryptionResult res = PasswordEncrypter.encryptNewPassword(paswoord);
        this.paswoord = res.getHash();
        this.salt = res.getSalt();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public void addAlarm(Alarm a) {
        alarms.add(a);
    }

    public void removeAlarm(Alarm a) {
        alarms.remove(a);
    }
    
    public String getGCMid()
    {
        return GCMid;
    }

    public void setGCMid(String GCMid) {
        this.GCMid = GCMid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.userid, other.userid)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userid);
        return hash;
    }

    public boolean authenticate(String password) {
        return PasswordEncrypter.isCorrect(getPaswoord(), password, getSalt());
    }

    @PreRemove
    @SuppressWarnings("unused")
    private void removeAlarms() {
        for (Alarm a : alarms) {
            a.removeUser(this);
        }
    }

    @PrePersist
    private void addusers() {
        for (Alarm a : alarms) {
            a.addUser(this);
        }
    }
}
