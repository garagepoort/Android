package be.cegeka.android.alarms.domain.entities;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RepeatedAlarm extends Alarm
{
    private int repeatUnit;
    private Integer repeatquantity;
    private long repeatEnddate;


    public RepeatedAlarm()
    {
        super();
    }


    public RepeatedAlarm(int repeatUnit, Integer repeatquantity, long repeatEnddate, Integer alarmid, String title, String info, long dateInMillis)
    {
        super(alarmid, title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatquantity = repeatquantity;
        this.repeatEnddate = repeatEnddate;
    }

    public RepeatedAlarm(int repeatUnit, Integer repeatquantity, long repeatEnddate, String title, String info, long dateInMillis)
    {
        super(title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatquantity = repeatquantity;
        this.repeatEnddate = repeatEnddate;
    }


    public int getRepeatUnit()
    {
        return repeatUnit;
    }


    public Integer getRepeatquantity()
    {
        return repeatquantity;
    }


    public long getRepeatEnddate()
    {
        return repeatEnddate;
    }


    public void setRepeatEnddate(long repeatEnddate)
    {
        this.repeatEnddate = repeatEnddate;
    }
    
    
    
}
