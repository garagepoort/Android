package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class TransferObjectMapper {

    public static final String NULL_ERROR_MESSAGE = "Argument is null";

    public UserTO convertUserToUserTO(User u) throws BusinessException {
        if (u == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new UserTO(u.getUserid(), u.getNaam(), u.getAchternaam(), u.getEmail(), u.isAdmin());
    }

    public User convertUserTOToUser(UserTO userTO) throws BusinessException {
        if (userTO == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new User(userTO.getUserid(), userTO.getNaam(), userTO.getAchternaam(), userTO.getPaswoord(), userTO.getEmail(), userTO.isAdmin());
    }

    public Alarm convertAlarmTOToAlarm(AlarmTO alarmTO) throws BusinessException {
        if (alarmTO == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        if (alarmTO instanceof RepeatedAlarmTO) {
            RepeatedAlarmTO rAlarmTO = (RepeatedAlarmTO) alarmTO;
            return new RepeatedAlarm(rAlarmTO.getRepeatUnit(), rAlarmTO.getRepeatQuantity(), rAlarmTO.getRepeatEnddate(), rAlarmTO.getAlarmID(), rAlarmTO.getTitle(), rAlarmTO.getInfo(), rAlarmTO.getDateInMillis());
        }
        return new Alarm(alarmTO.getAlarmID(), alarmTO.getTitle(), alarmTO.getInfo(), alarmTO.getDateInMillis());
    }
    
    public AlarmTO convertAlarmToAlarmTO(Alarm alarm) throws BusinessException {
        if (alarm == null) {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        if (alarm instanceof RepeatedAlarm) {
            RepeatedAlarm rAlarm = (RepeatedAlarm) alarm;
            return new RepeatedAlarmTO(rAlarm.getRepeatUnit(), rAlarm.getRepeatquantity(), rAlarm.getRepeatEnddate(), rAlarm.getAlarmid(), rAlarm.getTitle(), rAlarm.getInfo(), rAlarm.getDateInMillis());
        }
        return new AlarmTO(alarm.getAlarmid(), alarm.getTitle(), alarm.getInfo(), alarm.getDateInMillis());
    }
}
