/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cegeka.android.alarms.transferobjects.calendarExtensions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ivarv
 */
public enum CalendarUnitEnum {

    NA(0, "N/A"),
    MINUTE(12, "Minute"),
    HOUR(10, "Hour"),
    DAY(5, "Day"),
    WEEK(3, "Week"),
    MONTH(2, "Month"),
    YEAR(1, "Year");
    
    private static final Map<Integer, CalendarUnitEnum> lookup = new HashMap<Integer, CalendarUnitEnum>(CalendarUnitEnum.values().length, 1.0f);

    static {
        for (CalendarUnitEnum c : CalendarUnitEnum.values()) {
            lookup.put(c.calendarFieldValue, c);
        }
    }
    private int calendarFieldValue;
    private String displayName;

    CalendarUnitEnum(int calendarFieldValue, String displayName) {
        setCalendarFieldValue(calendarFieldValue);
        setDisplayName(displayName);
    }

    public int getCalendarFieldValue() {
        return calendarFieldValue;
    }

    public void setCalendarFieldValue(int calendarFieldValue) {
        this.calendarFieldValue = calendarFieldValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public static CalendarUnitEnum of(Integer calendarFieldValue) {
        CalendarUnitEnum result = lookup.get(calendarFieldValue);
        if (result == null) {
            throw new IllegalArgumentException("No CalendarUnit Exists");
        }
        return result;
    }
}
