/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandobjects;

import be.cegeka.android.alarms.transferobjects.calendarExtensions.CalendarUnitEnum;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author ivarv
 */
public class AlarmCommand {

    private Integer id = -1;
    private String title;
    private String info;
    private boolean repeated;
    private int repeatunit;
    private String repeatUnitName;
    private int repeatQuantity = 0;
    private String eventDateTimeString = formatCalendar(Calendar.getInstance());
    private String endRepeatDateTimeString = formatCalendar(Calendar.getInstance());

    public AlarmCommand(Integer id, String title, String info, boolean repeated, int repeatunit, Integer repeatQuantity, long endDate, long date) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.repeated = repeated;
        this.repeatunit = repeatunit;
        this.repeatUnitName = CalendarUnitEnum.of(repeatunit).getDisplayName();
        if (repeatQuantity != null) {
            this.repeatQuantity = repeatQuantity;
        }
        if (endDate != 0) {
            Calendar endDateCal = calendarFromLong(endDate);
            this.endRepeatDateTimeString = formatCalendar(endDateCal);
        }
        Calendar dateCal = calendarFromLong(date);
        this.eventDateTimeString = formatCalendar(dateCal);

    }

    private String formatCalendar(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    private long stringToMillis(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        cal.setTime(sdf.parse(date));
        return cal.getTimeInMillis();
    }

    public AlarmCommand() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public int getRepeatunit() {
        return this.repeatunit;
    }

    public void setRepeatunit(int repeatunit) {
        this.repeatunit = repeatunit;
    }

    public int getRepeatQuantity() {
        return repeatQuantity;
    }

    public void setRepeatQuantity(int repeatQuantity) {
        this.repeatQuantity = repeatQuantity;
    }

    public String getEventDateTimeString() {
        return eventDateTimeString;
    }

    public void setEventDateTimeString(String eventDateTimeString) {
        this.eventDateTimeString = eventDateTimeString;
    }

    public String getEndRepeatDateTimeString() {
        return endRepeatDateTimeString;
    }

    public void setEndRepeatDateTimeString(String endRepeatDateTimeString) {
        this.endRepeatDateTimeString = endRepeatDateTimeString;
    }

    private Calendar calendarFromLong(long date) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTimeInMillis(date);
        return dateCalendar;
    }

    private Calendar calendarFromBigInt(BigInteger endDate) {
        Calendar repeatEndCalendar = Calendar.getInstance();
        long endDateMillis = endDate.longValue();
        repeatEndCalendar.setTimeInMillis(endDateMillis);
        return repeatEndCalendar;
    }

    public long getEventDateInMillis() throws ParseException {
        return stringToMillis(getEventDateTimeString());
    }

    public long getEndDateInMillis() throws ParseException {
        return stringToMillis(getEndRepeatDateTimeString());
    }

    public String getRepeatUnitName() {
        return CalendarUnitEnum.of(repeatunit).getDisplayName();
    }

    
}
