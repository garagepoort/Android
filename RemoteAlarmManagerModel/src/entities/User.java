/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import domain.PasswordEncrypter;
import exceptions.DatabaseException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @NamedQuery(name = "User.findBySalt", query = "SELECT u FROM User u WHERE u.salt = :salt")})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private List<UserAlarm> userAlarmList;

    public User() {
    }

    public User(Integer userid) {
        this.userid = userid;
    }

    public User(String naam, String achternaam, String paswoord, String email) {
        setNaam(naam);
        setAchternaam(achternaam);
        setPaswoord(paswoord);
        setEmail(email);
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
        return this.paswoord;
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

    public boolean authenticate(String password) {
        return PasswordEncrypter.isCorrect(getPaswoord(), password, getSalt());
    }

    private String md5(String input) throws DatabaseException {
        String md5 = null;
        if (null == input) {
            return null;
        }
        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new DatabaseException(e);
        }
        return md5;
    }

    private String sha1(String input) throws DatabaseException {
        String sha1 = null;
        if (null == input) {
            return null;
        }
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(input.getBytes(), 0, input.length());
            sha1 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new DatabaseException(e);
        }
        return sha1;
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
}
