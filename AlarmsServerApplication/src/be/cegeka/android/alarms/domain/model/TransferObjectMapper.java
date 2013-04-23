package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;


public class TransferObjectMapper
{
    public static final String NULL_ERROR_MESSAGE = "Argument is null";
    
    
    public AlarmTO convertAlarmToAlarmTO(Alarm alarm) throws BusinessException
    {
        if (alarm == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new AlarmTO(alarm.getAlarmid(), alarm.getTitle(), alarm.getInfo(), alarm.getDateInMillis());
    }


    public UserTO convertUserToUserTO(User u) throws BusinessException
    {
        if (u == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new UserTO(u.getUserid(), u.getNaam(), u.getAchternaam(), u.getEmail(), u.isAdmin());
    }


    public User convertUserTOToUser(UserTO userTO) throws BusinessException
    {
        if (userTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new User(userTO.getUserid(), userTO.getNaam(), userTO.getAchternaam(), userTO.getPaswoord(), userTO.getEmail(), userTO.isAdmin());
    }


    public Alarm convertAlarmTOToAlarm(AlarmTO alarmTO) throws BusinessException
    {
        if (alarmTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new Alarm(alarmTO.getAlarmID(), alarmTO.getTitle(), alarmTO.getInfo(), alarmTO.getDateInMillis());
    }


    public RepeatedAlarmTO convertRepeatedAlarmToRepeatedAlarmTO(RepeatedAlarm repeatedAlarm) throws BusinessException
    {
        if (repeatedAlarm == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new RepeatedAlarmTO(repeatedAlarm.getRepeatUnit(), repeatedAlarm.getRepeatquantity(), repeatedAlarm.getRepeatEnddate(), repeatedAlarm.getAlarmid(), repeatedAlarm.getTitle(), repeatedAlarm.getInfo(), repeatedAlarm.getDateInMillis());
    }


    public RepeatedAlarm convertRepeatedAlarmTOToRepeatedAlarm(RepeatedAlarmTO repeatedAlarmTO) throws BusinessException
    {
        if (repeatedAlarmTO == null)
        {
            throw new BusinessException(NULL_ERROR_MESSAGE);
        }
        return new RepeatedAlarm(repeatedAlarmTO.getRepeatUnit(), repeatedAlarmTO.getRepeatQuantity(), repeatedAlarmTO.getRepeatEnddate(), repeatedAlarmTO.getAlarmID(), repeatedAlarmTO.getTitle(), repeatedAlarmTO.getInfo(), repeatedAlarmTO.getDateInMillis());
    }
}


