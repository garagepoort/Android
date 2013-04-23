package be.cegeka.android.alarms.transferobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class AlarmTO
{
    private Integer alarmID;
    private String title;
    private String info;
    private long dateInMillis;


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
        if (!Objects.equals(this.title, other.title))
        {
            return false;
        }
        if (!Objects.equals(this.info, other.info))
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
