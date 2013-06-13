/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import domain.PasswordEncrypter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivarv
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserid", query = "SELECT u FROM User u WHERE u.userid = :userid"),
    @NamedQuery(name = "User.findByNaam", query = "SELECT u FROM User u WHERE u.naam = :naam"),
    @NamedQuery(name = "User.findByAchternaam", query = "SELECT u FROM User u WHERE u.achternaam = :achternaam"),
    @NamedQuery(name = "User.findByPaswoord", query = "SELECT u FROM User u WHERE u.paswoord = :paswoord"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findBySalt", query = "SELECT u FROM User u WHERE u.salt = :salt"),
    @NamedQuery(name = "User.findByAdmin", query = "SELECT u FROM User u WHERE u.admin = :admin")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userid")
    private Integer userid;
    @Basic(optional = false)
    @Column(name = "naam")
    private String naam;
    @Basic(optional = false)
    @Column(name = "achternaam")
    private String achternaam;
    @Basic(optional = false)
    @Column(name = "paswoord")
    private String paswoord;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "salt")
    private String salt;
    @Column(name = "admin")
    private Boolean admin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<UserAlarm> userAlarmList;

    public User() {
    }

    public User(Integer userid) {
        this.userid = userid;
    }

    public User(String naam, String achternaam, String paswoord, String email, boolean admin) {
        setNaam(naam);
        setAchternaam(achternaam);
        setPaswoord(paswoord);
        setEmail(email);
        setAdmin(admin);
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

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getPaswoord() {
        return paswoord;
    }

    public void setPaswoord(String paswoord) {
        PasswordEncrypter.EncryptionResult res = PasswordEncrypter.encryptNewPassword(paswoord);
        this.paswoord = res.getHash();
        this.salt = res.getSalt();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @XmlTransient
    public List<UserAlarm> getUserAlarmList() {
        return userAlarmList;
    }

    public void setUserAlarmList(List<UserAlarm> userAlarmList) {
        this.userAlarmList = userAlarmList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ userid=" + userid + " ]";
    }

    public boolean authenticate(String password) {
        return PasswordEncrypter.isCorrect(getPaswoord(), password, getSalt());
    }
}
