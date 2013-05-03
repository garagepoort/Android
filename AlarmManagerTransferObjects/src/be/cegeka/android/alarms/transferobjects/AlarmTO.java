package be.cegeka.android.alarms.transferobjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AlarmTO implements Serializable
{
    private Integer alarmID;
    private String title;
    private String info;
    private long dateInMillis;


    public AlarmTO()
    {
    }


    public AlarmTO(Integer alarmID, String title, String info, long dateInMillis)
    {
        setAlarmID(alarmID);
        setTitle(title);
        setInfo(info);
        setDateInMillis(dateInMillis);
    }


    public Integer getAlarmID()
    {
        return alarmID;
    }


    public void setAlarmID(Integer alarmID)
    {
        this.alarmID = alarmID;
    }


    public String getTitle()
    {
        return title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getInfo()
    {
        return info;
    }


    public void setInfo(String info)
    {
        this.info = info;
    }


    public long getDateInMillis()
    {
        return dateInMillis;
    }


    public void setDateInMillis(long dateInMillis)
    {
        this.dateInMillis = dateInMillis;
    }


    @Override
    public int hashCode()
    {
        int hash = 3;
        return hash;
    }


    @Override
    public String toString()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return title + "\n" + dateFormat.format(calendar.getTime());
    }


    public String toDetailedString()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return title + "\n" + info + "\n" + dateFormat.format(calendar.getTime());
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
        final AlarmTO other = (AlarmTO) obj;
        if (!this.title.equals(other.title))
        {
            return false;
        }
        if (!this.info.equals(other.info))
        {
            return false;
        }
        if (this.dateInMillis != other.dateInMillis)
        {
            return false;
        }

        return true;
    }
}


