/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivarv
 */
@Entity
@Table(name = "user_alarm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAlarm.findAll", query = "SELECT u FROM UserAlarm u"),
    @NamedQuery(name = "UserAlarm.findByUseralarmid", query = "SELECT u FROM UserAlarm u WHERE u.useralarmid = :useralarmid")})
public class UserAlarm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "useralarmid")
    private Integer useralarmid;
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private User userid;
    @JoinColumn(name = "alarmid", referencedColumnName = "alarmid")
    @ManyToOne(optional = false)
    private Alarm alarmid;

    public UserAlarm() {
    }

    public UserAlarm(Integer useralarmid) {
        this.useralarmid = useralarmid;
    }
    
    public UserAlarm(User user, Alarm alarm){
        setUserid(user);
        setAlarmid(alarm);
    }

    public Integer getUseralarmid() {
        return useralarmid;
    }

    public void setUseralarmid(Integer useralarmid) {
        this.useralarmid = useralarmid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public Alarm getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(Alarm alarmid) {
        this.alarmid = alarmid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (useralarmid != null ? useralarmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAlarm)) {
            return false;
        }
        UserAlarm other = (UserAlarm) object;
        if ((this.useralarmid == null && other.useralarmid != null) || (this.useralarmid != null && !this.useralarmid.equals(other.useralarmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserAlarm[ useralarmid=" + useralarmid + " ]";
    }
    
}
