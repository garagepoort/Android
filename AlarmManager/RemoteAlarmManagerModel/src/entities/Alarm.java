/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "alarm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alarm.findAll", query = "SELECT a FROM Alarm a"),
    @NamedQuery(name = "Alarm.findByAlarmid", query = "SELECT a FROM Alarm a WHERE a.alarmid = :alarmid"),
    @NamedQuery(name = "Alarm.findByTitle", query = "SELECT a FROM Alarm a WHERE a.title = :title"),
    @NamedQuery(name = "Alarm.findByInfo", query = "SELECT a FROM Alarm a WHERE a.info = :info"),
    @NamedQuery(name = "Alarm.findByRepeated", query = "SELECT a FROM Alarm a WHERE a.repeated = :repeated"),
    @NamedQuery(name = "Alarm.findByRepeatUnit", query = "SELECT a FROM Alarm a WHERE a.repeatUnit = :repeatUnit"),
    @NamedQuery(name = "Alarm.findByRepeatquantity", query = "SELECT a FROM Alarm a WHERE a.repeatquantity = :repeatquantity"),
    @NamedQuery(name = "Alarm.findByRepeatEnddate", query = "SELECT a FROM Alarm a WHERE a.repeatEnddate = :repeatEnddate"),
    @NamedQuery(name = "Alarm.findByDateInMillis", query = "SELECT a FROM Alarm a WHERE a.dateInMillis = :dateInMillis")})
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "alarmid")
    private Integer alarmid;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "info")
    private String info;
    @Basic(optional = false)
    @Column(name = "repeated")
    private boolean repeated;
    @Column(name = "repeat_unit")
    private String repeatUnit;
    @Column(name = "repeatquantity")
    private Integer repeatquantity;
    @Column(name = "repeat_enddate")
    private BigInteger repeatEnddate;
    @Basic(optional = false)
    @Column(name = "date_in_millis")
    private long dateInMillis;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alarmid")
    private List<UserAlarm> userAlarmList;
    
    public Alarm(){
        
    }

    public Alarm(Integer alarmid) {
        this.alarmid = alarmid;
    }

    public Alarm(String title, String info, boolean repeated, long dateInMillis) {
        this.title = title;
        this.info = info;
        this.repeated = repeated;
        this.dateInMillis = dateInMillis;
    }

    public Alarm(Integer alarmid, String title, String info, boolean repeated, String repeatUnit, long dateInMillis, Integer repeatquantity, BigInteger repeatEnddate) {
        this.alarmid = alarmid;
        this.title = title;
        this.info = info;
        this.repeated = repeated;
        this.repeatUnit = repeatUnit;
        this.dateInMillis = dateInMillis;
        this.repeatquantity = repeatquantity;
        this.repeatEnddate = repeatEnddate;
    }

    public Alarm(String title, String info, boolean repeated, String repeatUnit, long dateInMillis, Integer repeatquantity, BigInteger repeatEnddate) {
        this.title = title;
        this.info = info;
        this.repeated = repeated;
        this.repeatUnit = repeatUnit;
        this.dateInMillis = dateInMillis;
        this.repeatquantity = repeatquantity;
        this.repeatEnddate = repeatEnddate;
    }

    public Integer getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(Integer alarmid) {
        this.alarmid = alarmid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean getRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public String getRepeatUnit() {
        return repeatUnit;
    }

    public void setRepeatUnit(String repeatUnit) {
        this.repeatUnit = repeatUnit;
    }

    public Integer getRepeatquantity() {
        return repeatquantity;
    }

    public void setRepeatquantity(Integer repeatquantity) {
        this.repeatquantity = repeatquantity;
    }

    public BigInteger getRepeatEnddate() {
        return repeatEnddate;
    }

    public void setRepeatEnddate(BigInteger repeatEnddate) {
        this.repeatEnddate = repeatEnddate;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
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
        hash += (alarmid != null ? alarmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alarm)) {
            return false;
        }
        Alarm other = (Alarm) object;
        if ((this.alarmid == null && other.alarmid != null) || (this.alarmid != null && !this.alarmid.equals(other.alarmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Alarm[ alarmid=" + alarmid + " ]";
    }
}
