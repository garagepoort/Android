package be.cegeka.android.alarms.transferobjects;

import java.util.List;
import java.util.Objects;

public class RepeatedAlarmTO extends AlarmTO {

    private int repeatUnit;
    private Integer repeatQuantity;
    private long repeatEnddate;

    public RepeatedAlarmTO(int repeatUnit, Integer repeatQuantity, long repeatEnddate, Integer alarmID, String title, String info, long dateInMillis) {
        super(alarmID, title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatQuantity = repeatQuantity;
        this.repeatEnddate = repeatEnddate;
    }

    public RepeatedAlarmTO(AlarmTO alarm) {
        super(alarm.getAlarmID(), alarm.getTitle(), alarm.getInfo(), alarm.getDateInMillis());
        if (alarm instanceof RepeatedAlarmTO) {
            RepeatedAlarmTO rAlarm = (RepeatedAlarmTO) alarm;
            this.repeatUnit = rAlarm.getRepeatUnit();
            this.repeatQuantity = rAlarm.getRepeatQuantity();
            this.repeatEnddate = rAlarm.getRepeatEnddate();
        }
    }

    public int getRepeatUnit() {
        return repeatUnit;
    }

    public Integer getRepeatQuantity() {
        return repeatQuantity;
    }

    public void setRepeatUnit(int repeatUnit) {
        this.repeatUnit = repeatUnit;
    }

    public void setRepeatQuantity(Integer repeatQuantity) {
        this.repeatQuantity = repeatQuantity;
    }
    
    public long getRepeatEnddate() {
        return repeatEnddate;
    }

    public void setRepeatEnddate(long repeatEnddate) {
        this.repeatEnddate = repeatEnddate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepeatedAlarmTO other = (RepeatedAlarmTO) obj;
        if (!Objects.equals(this.getTitle(), other.getTitle())) {
            return false;
        }
        if (!Objects.equals(this.getInfo(), other.getInfo())) {
            return false;
        }
        if (this.getDateInMillis() != other.getDateInMillis()) {
            return false;
        }
        if (this.repeatUnit != other.repeatUnit) {
            return false;
        }
        if (!Objects.equals(this.repeatQuantity, other.repeatQuantity)) {
            return false;
        }
        if (this.repeatEnddate != other.repeatEnddate) {
            return false;
        }
        return true;
    }
}
