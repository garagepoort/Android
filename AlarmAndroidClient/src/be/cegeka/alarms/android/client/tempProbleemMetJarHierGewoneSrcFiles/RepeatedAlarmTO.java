package be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles;




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

    

    public int getRepeatUnit()
    {
        return repeatUnit;
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
    
}
