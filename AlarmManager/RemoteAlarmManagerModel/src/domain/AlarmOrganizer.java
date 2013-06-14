/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import db.RelationalDatabase;
import entities.Alarm;
import entities.User;
import exceptions.DatabaseException;
import java.util.List;

/**
 *
 * @author ivarv
 */
public class AlarmOrganizer {

    private RelationalDatabase db;
    
    public AlarmOrganizer() {
        db = RelationalDatabase.getInstance();
    }

    public User createUser(User user) throws DatabaseException {
        return db.createUser(user);
    }

    public User getUser(int id) throws DatabaseException {
        return db.getUser(id);
    }

    public void deleteUser(User user) throws DatabaseException {
        db.deleteUser(user);
    }

    public void deleteUser(int id) throws DatabaseException {
        db.deleteUser(id);
    }

    public User updateUser(User user) throws DatabaseException {
        return db.updateUser(user);
    }

    public List<User> getAllUsers() {
        return db.getAllUsers();
    }

    public List<Alarm> getAlarmsFromUser(User user) throws DatabaseException {
        return db.getAlarmsFromUser(user);
    }

    public User getUser(String email) throws DatabaseException {
        return db.getUser(email);
    }

    public Alarm createAlarm(Alarm alarm) throws DatabaseException {
        return db.createAlarm(alarm);
    }

    public Alarm getAlarm(int id) throws DatabaseException {
        return db.getAlarm(id);
    }

    public void deleteAlarm(Alarm alarm) throws DatabaseException {
        db.deleteAlarm(alarm);
    }

    public void deleteAlarm(int id) throws DatabaseException {
        db.deleteAlarm(id);
    }

    public Alarm updateAlarm(Alarm alarm) throws DatabaseException {
        return db.updateAlarm(alarm);
    }

    public List<Alarm> getAllAlarms() {
        return db.getAllAlarms();
    }

    public List<User> getUsersFromAlarm(Alarm alarm) throws DatabaseException {
        return db.getUsersFromAlarm(alarm);
    }

    public User addAlarmToUser(User user, Alarm alarm) throws DatabaseException {
        return db.addAlarmToUser(user, alarm);
    }

    public User addAlarmToUser(int userId, int alarmId) throws DatabaseException {
        return db.addAlarmToUser(userId, alarmId);
    }

    public User removeAlarmFromUser(User user, Alarm alarm) throws DatabaseException {
        return db.removeAlarmFromUser(user, alarm);
    }

    public User removeAlarmFromUser(int userId, int alarmId) throws DatabaseException {
        return db.removeAlarmFromUser(userId, alarmId);
    }

    public void cleanUpAlarms() throws DatabaseException {
        db.cleanUpAlarms();
    }

    public void closeConnection() {
        db.closeConnection();
    }
    
    public boolean checkIfEmailExists(String email) throws DatabaseException{
        return db.checkIfEmailExists(email);
    }
    
    public void upgradeToAdmin(User source, User target) throws DatabaseException {
        db.upgradeToAdmin(source, target);
    }
    
    public void downgradeToUser(User source, User target) throws DatabaseException {
        db.downgradeToUser(source, target);
    }
}
