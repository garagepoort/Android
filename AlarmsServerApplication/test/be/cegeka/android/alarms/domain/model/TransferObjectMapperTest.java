package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.Alarm;
import be.cegeka.android.alarms.domain.entities.RepeatedAlarm;
import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TransferObjectMapperTest
{
    private TransferObjectMapper transferObjectMapper;
    private AlarmTO alarmTO;
    private UserTO userTO;
    private RepeatedAlarmTO repeatedAlarmTO;
    private Alarm alarm;
    private User user;
    private RepeatedAlarm repeatedAlarm;


    @Before
    public void setUp()
    {
        transferObjectMapper = new TransferObjectMapper();
        alarmTO = new AlarmTO(1, "alarmtitel", "alarminfo", 120000);
        repeatedAlarmTO = new RepeatedAlarmTO(Calendar.DAY_OF_MONTH, 515, 516516516, 516516516, "testtitel", "testinfo", 4556565);
        userTO = new UserTO(4, "naam", "achternaam", "pas", "pas", "email", Boolean.TRUE);

        alarm = new Alarm("alarm", "info", 58898987);
        repeatedAlarm = new RepeatedAlarm(Calendar.DAY_OF_MONTH, 84846546, 54654654, "fezfz", "ezfze", 4544654);
        user = new User("naam", "dzead", "pas", "pas", Boolean.FALSE);
    }


    @After
    public void tearDown()
    {
    }


    @Test
    public void testConvertAlarmToAlarmTO() throws BusinessException
    {
        AlarmTO ato = transferObjectMapper.convertAlarmToAlarmTO(alarm);
        assertEquals(ato.getAlarmID(), alarm.getAlarmid());
        assertEquals(ato.getDateInMillis(), alarm.getDateInMillis());
        assertEquals(ato.getInfo(), alarm.getInfo());
        assertEquals(ato.getTitle(), alarm.getTitle());
    }


    @Test
    public void testConvertUserToUserTO() throws BusinessException
    {
        UserTO uto = transferObjectMapper.convertUserToUserTO(user);
        assertEquals(uto.getUserid(), user.getUserid());
        assertEquals(uto.getNaam(), user.getNaam());
        assertEquals(uto.getAchternaam(), user.getAchternaam());
        assertEquals(uto.getEmail(), user.getEmail());
        assertEquals(uto.isAdmin(), user.isAdmin());
        assertEquals(null, uto.getPaswoord());
        assertEquals(null, uto.getRepeatPaswoord());
    }

    @Test
    public void testConvertUserTOToUser() throws BusinessException
    {
        User u = transferObjectMapper.convertUserTOToUser(userTO);
        assertEquals(u.getUserid(), userTO.getUserid());
        assertEquals(u.getNaam(), userTO.getNaam());
        assertEquals(u.getAchternaam(), userTO.getAchternaam());
        assertEquals(u.getEmail(), userTO.getEmail());
        assertEquals(u.isAdmin(), userTO.isAdmin());
    }


    @Test
    public void testConvertAlarmTOToAlarm() throws BusinessException
    {
        Alarm a = transferObjectMapper.convertAlarmTOToAlarm(alarmTO);
        assertEquals(a.getAlarmid(), alarmTO.getAlarmID());
        assertEquals(a.getDateInMillis(), alarmTO.getDateInMillis());
        assertEquals(a.getInfo(), alarmTO.getInfo());
        assertEquals(a.getTitle(), alarmTO.getTitle());
    }


    @Test
    public void testConvertRepeatedAlarmToRepeatedAlarmTO() throws BusinessException
    {
        RepeatedAlarmTO r = transferObjectMapper.convertRepeatedAlarmToRepeatedAlarmTO(repeatedAlarm);
        assertTrue(r instanceof RepeatedAlarmTO);
        assertEquals(r.getAlarmID(), repeatedAlarm.getAlarmid());
        assertEquals(r.getDateInMillis(), repeatedAlarm.getDateInMillis());
        assertEquals(r.getInfo(), repeatedAlarm.getInfo());
        assertEquals(r.getTitle(), repeatedAlarm.getTitle());
        assertEquals(r.getRepeatUnit(), repeatedAlarm.getRepeatUnit());
        assertEquals(r.getRepeatQuantity(), repeatedAlarm.getRepeatquantity());
        assertEquals(r.getRepeatEnddate(), repeatedAlarm.getRepeatEnddate());
    }


    @Test
    public void testConvertRepeatedAlarmTOToRepeatedAlarm() throws BusinessException
    {
        RepeatedAlarm r = transferObjectMapper.convertRepeatedAlarmTOToRepeatedAlarm(repeatedAlarmTO);
        assertTrue(r instanceof RepeatedAlarm);
        assertEquals(r.getAlarmid(), repeatedAlarmTO.getAlarmID());
        assertEquals(r.getDateInMillis(), repeatedAlarmTO.getDateInMillis());
        assertEquals(r.getInfo(), repeatedAlarmTO.getInfo());
        assertEquals(r.getTitle(), repeatedAlarmTO.getTitle());
        assertEquals(r.getRepeatUnit(), repeatedAlarmTO.getRepeatUnit());
        assertEquals(r.getRepeatquantity(), repeatedAlarmTO.getRepeatQuantity());
        assertEquals(r.getRepeatEnddate(), repeatedAlarmTO.getRepeatEnddate());
    }


    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertAlarmToAlarmTO() throws BusinessException
    {
        transferObjectMapper.convertAlarmToAlarmTO(null);
    }


    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertAlarmTOToAlarm() throws BusinessException
    {
        transferObjectMapper.convertAlarmTOToAlarm(null);
    }

    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertUserToUserTO() throws BusinessException
    {
        transferObjectMapper.convertUserToUserTO(null);
    }

    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertUserTOToUser() throws BusinessException
    {
        transferObjectMapper.convertUserTOToUser(null);
    }

    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertRepeatedAlarmToReapeatedAlarmTO() throws BusinessException
    {
        transferObjectMapper.convertRepeatedAlarmToRepeatedAlarmTO(null);
    }

    @Test(expected = BusinessException.class)
    public void testBusinessExceptionThrownWhenNullAsArgumentForConvertRepeatedAlarmTOToReapeatedAlarm() throws BusinessException
    {
        transferObjectMapper.convertRepeatedAlarmTOToRepeatedAlarm(null);
    }
}