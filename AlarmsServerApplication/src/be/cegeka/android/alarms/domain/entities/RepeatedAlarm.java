package be.cegeka.android.alarms.domain.entities;

import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;



@Entity
@PrimaryKeyJoinColumn(name="alarmid", referencedColumnName="alarmid")
public class RepeatedAlarm extends Alarm
{
    private String repeatUnit;
    private Integer repeatquantity;
    private BigInteger repeatEnddate;


    public RepeatedAlarm()
    {
        super();
    }


    public RepeatedAlarm(String repeatUnit, Integer repeatquantity, BigInteger repeatEnddate, String title, String info, long dateInMillis)
    {
        super(title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatquantity = repeatquantity;
        this.repeatEnddate = repeatEnddate;
    }


    public String getRepeatUnit()
    {
        return repeatUnit;
    }


    public Integer getRepeatquantity()
    {
        return repeatquantity;
    }


    public BigInteger getRepeatEnddate()
    {
        return repeatEnddate;
    }


    public void setRepeatEnddate(BigInteger repeatEnddate)
    {
        this.repeatEnddate = repeatEnddate;
    }
    
    
    
}
