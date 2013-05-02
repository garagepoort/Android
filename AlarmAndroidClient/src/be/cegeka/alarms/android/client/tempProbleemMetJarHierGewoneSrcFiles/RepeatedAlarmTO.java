package be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;




public class RepeatedAlarmTO extends AlarmTO
{
    
    private int repeatUnit;
    private Integer repeatQuantity;
    private long repeatEnddate;
    
    public RepeatedAlarmTO(AlarmTO alarm){
    	super(alarm.getAlarmID(), alarm.getTitle(), alarm.getInfo(), alarm.getDateInMillis());
    }

    public RepeatedAlarmTO(int repeatUnit, Integer repeatQuantity, long repeatEnddate, Integer alarmID, String title, String info, long dateInMillis)
    {
        super(alarmID, title, info, dateInMillis);
        this.repeatUnit = repeatUnit;
        this.repeatQuantity = repeatQuantity;
        this.repeatEnddate = repeatEnddate;
    }

    

    public int getRepeatUnit()
    {
        return repeatUnit;
    }
    
    

    public void setRepeatUnit(int repeatUnit) {
		this.repeatUnit = repeatUnit;
	}

	public void setRepeatQuantity(Integer repeatQuantity) {
		this.repeatQuantity = repeatQuantity;
	}

	public Integer getRepeatQuantity()
    {
        return repeatQuantity;
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
    
    @SuppressLint("SimpleDateFormat")
	@Override
	public String toString()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getDateInMillis());
		return "RepeatedAlarmTO [title= " + getTitle() + ", info= " + getInfo() + ", date= " + new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()) + "repeatUnit= "+ getRepeatUnit() + " repeatQuantity= " + getRepeatQuantity() +"]";
	}
    
}