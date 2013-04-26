/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commandobjects;

import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import java.text.ParseException;

/**
 *
 * @author ivarv
 */
public class CommandObjectConverter {
    
    public static AlarmCommand convertAlarmTOToAlarmCommandObject(AlarmTO alarm){
        if(alarm instanceof RepeatedAlarmTO){
            RepeatedAlarmTO rAlarmTO = (RepeatedAlarmTO) alarm;
            return new AlarmCommand(alarm.getAlarmID(), alarm.getTitle(), alarm.getInfo(), true, rAlarmTO.getRepeatUnit(), rAlarmTO.getRepeatQuantity(), rAlarmTO.getRepeatEnddate(), alarm.getDateInMillis());
        }
        else {
            return new AlarmCommand(alarm.getAlarmID(), alarm.getTitle(), alarm.getInfo(), false, 0, 0, 0, alarm.getDateInMillis());
        }
    }
    
    public static AlarmTO convertAlarmCommandObjectToAlarmTO(AlarmCommand alarm) throws ParseException{
        if(!alarm.isRepeated()){
            return new AlarmTO(alarm.getId(), alarm.getTitle(), alarm.getInfo(), alarm.getEventDateInMillis());
        }
        else {
            return new RepeatedAlarmTO(alarm.getRepeatunit(), alarm.getRepeatQuantity(), alarm.getEndDateInMillis(), alarm.getId(), alarm.getTitle(), alarm.getInfo(), alarm.getEventDateInMillis());
        }
    }
    
}
