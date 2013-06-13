package be.cegeka.android.alarms.transferobjects;

import be.cegeka.android.alarms.transferobjects.calendarExtensions.CalendarUnitEnum;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class RepeatedAlarmTO extends AlarmTO
{
    private int repeatUnit;
    private Integer repeatQuantity;
    private long repeatEnddate;


    public RepeatedAlarmTO(int repeatUnit, Integer repeatQuantity, long repeatEnddate, Integer alarmID, String title, String info, long dateInMillis)
    {
        super(alarmID, title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatQuantity = repeatQuantity;
        this.repeatEnddate = repeatEnddate;
    }


    public RepeatedAlarmTO(AlarmTO alarm)
    {
        super(alarm.getAlarmID(), alarm.getTitle(), alarm.getInfo(), alarm.getDateInMillis());
        if (alarm instanceof RepeatedAlarmTO)
        {
            RepeatedAlarmTO rAlarm = (RepeatedAlarmTO) alarm;
            this.repeatUnit = rAlarm.getRepeatUnit();
            this.repeatQuantity = rAlarm.getRepeatQuantity();
            this.repeatEnddate = rAlarm.getRepeatEnddate();
        }
    }


    public int getRepeatUnit()
    {
        return repeatUnit;
    }


    public Integer getRepeatQuantity()
    {
        return repeatQuantity;
    }


    public void setRepeatUnit(int repeatUnit)
    {
        this.repeatUnit = repeatUnit;
    }


    public void setRepeatQuantity(Integer repeatQuantity)
    {
        this.repeatQuantity = repeatQuantity;
    }


    public long getRepeatEnddate()
    {
        return repeatEnddate;
    }


    public void setRepeatEnddate(long repeatEnddate)
    {
        this.repeatEnddate = repeatEnddate;
    }


    @Override
    public int hashCode()
    {
        int hash = 5;
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
        final RepeatedAlarmTO other = (RepeatedAlarmTO) obj;
        if (!this.getTitle().equals(other.getTitle()))
        {
            return false;
        }
        if (!this.getInfo().equals(other.getInfo()))
        {
            return false;
        }
        if (this.getDateInMillis() != other.getDateInMillis())
        {
            return false;
        }
        if (this.repeatUnit != other.repeatUnit)
        {
            return false;
        }
        if (!this.repeatQuantity.equals(other.repeatQuantity))
        {
            return false;
        }
        if (this.repeatEnddate != other.repeatEnddate)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return super.toString() + "\nRepeated Alarm";
    }


    @Override
    public String toDetailedString()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(repeatEnddate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        if (repeatQuantity == 1)
        {
            return super.toDetailedString() + "\nRepeated every " + CalendarUnitEnum.of(repeatUnit).toString().toLowerCase() + " untill\n" + dateFormat.format(calendar.getTime());
        }
        else
        {
            return super.toDetailedString() + "\nRepeated every " + repeatQuantity + " " + CalendarUnitEnum.of(repeatUnit).toString().toLowerCase() + "s untill\n" + dateFormat.format(calendar.getTime());
        }
    }
}


