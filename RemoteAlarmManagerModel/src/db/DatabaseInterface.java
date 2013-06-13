package db;

import entities.Alarm;
import entities.User;
import exceptions.DatabaseException;
import java.util.List;

/**
 *
 * @author ivar
 */
public interface DatabaseInterface {
    
    // *********************
    // **USER CRUD METHODS**
    // *********************
    
    /**
     * Persists a user object in the database.
     * @param user The user that must be persisted.
     * @return A user object with an ID.
     * @throws DatabaseException when user is null.
     */
    public User createUser(User user) throws DatabaseException;
    
    /**
     * Gets a user from the database, based on the given ID.
     * @param id The ID of the user in the database.
     * @return a User object from the database.
     * @throws DatabaseException 
     */
    public User getUser(int id) throws DatabaseException;
    
    /**
     * Deletes a user from the database.
     * @param user The user that must be deleted.
     * @throws DatabaseException  when the user is null.
     */
    public void deleteUser(User user) throws DatabaseException;
    
    /**
     * Deletes a user from the database.
     * @param id The id from the user that must be deleted.
     * @throws DatabaseException when there is no user with this ID.
     */
    public void deleteUser(int id) throws DatabaseException;
    
    /**
     * Updates a user in the database with the values from the given user object.
     * @param user An edited user object with new values.
     * @return The updated user.
     * @throws DatabaseException If user is null. 
     */
    public User updateUser(User user) throws DatabaseException;
    
    /**
     * Returns a list of all users from the database.
     * @return  A list of all users in the database.
     */
    public List<User> getAllUsers();
    
    /**
     * Returns a list of alarms from the given user.
     * @param user The user whose alarms you need.
     * @return A list of alarms that are linked to the given user.
     * @throws DatabaseException If the user is null.
     */
    public List<Alarm> getAlarmsFromUser(User user) throws DatabaseException;
    
    /**
     * Gets a user from the database based on the given e-mail address.
     * @param email The e-mail from the user you want.
     * @return A user object that has the given e-mail.
     * @throws DatabaseException when the given e-mail is empty or null.
     */
    public User getUser(String email) throws DatabaseException;
    
    /**
     * Checks if there is a user with this e-mail address.
     * @param email The e-mail address you want to check.
     * @return A boolean indicating if there's a user with the provided e-mail address.
     * @throws DatabaseException when the provided e-mail address is empty or null.
     */
    public boolean checkIfEmailExists(String email) throws DatabaseException;
    
    /**
     * Removes all the alarms from the given user.
     * @param user The user whose alarms you want to unlink.
     * @throws DatabaseException  if the user is null.
     */
    public void clearAlarmsFromUser(User user) throws DatabaseException;
    
    /** 
     * Upgrades a user to an admin.
     * @param source The user who executes the upgrade. (Must be an admin.)
     * @param target The user who will be upgraded. (Should be a user.)
     * @throws DatabaseException If source or target is null or source is not an admin.
     */
    public void upgradeToAdmin(User source, User target) throws DatabaseException;
    
    /**
     * Downgrades an admin to user status.
     * @param source The user who executes the downgrade. (Must be an admin.)
     * @param target The user who will be downgraded. (Should be an admin.)
     * @throws DatabaseException If the source or the target is null or the source is not an admin.
     */
    public void downgradeToUser(User source, User target) throws DatabaseException;
    
    // ********************
    // *ALARM CRUD METHODS*
    // ********************
    
    /**
     * Persists an alar in the database?
     * @param alarm The alarm that must be persisted.
     * @return an Alarm with an ID.
     * @throws DatabaseException  when the provided alarm is null.
     */
    public Alarm createAlarm(Alarm alarm) throws DatabaseException;
    
    /**
     * Gets an alarm from the database based on an ID.
     * @param id The id of the alarm you want from the database.
     * @return An Alarm from the Database.
     * @throws DatabaseException
     */
    public Alarm getAlarm(int id) throws DatabaseException;
    
    /**
     * Deletes an alarm from the database.
     * @param alarm The alarm that must be deleted.
     * @throws DatabaseException when the provided alarm is null.
     */
    public void deleteAlarm(Alarm alarm) throws DatabaseException;
    
    /**
     * Deletes an alarm from the database with the given ID.
     * @param id The ID from the alarm you want to delete.
     * @throws DatabaseException 
     */
    public void deleteAlarm(int id) throws DatabaseException;
    
    /**
     * Updates an alarm in the database with the provided values.
     * @param alarm The alarm with the updated values.
     * @return An updated alarm.
     * @throws DatabaseException When the provided alarm is null. 
     */
    public Alarm updateAlarm(Alarm alarm) throws DatabaseException;
    
    /**
     * Returns a list with all the alarms from the database.
     * @return A List of alarms.
     */
    public List<Alarm> getAllAlarms();
    
    /**
     * Returns all users linked to the given alarm.
     * @param alarm The alarm whose linked users you want.
     * @return a List of user objects.
     * @throws DatabaseException if the given alarm is null.
     */
    public List<User> getUsersFromAlarm(Alarm alarm) throws DatabaseException;
    
    // *******************************
    // *RELATIONSHIP MANAGING METHODS*
    // *******************************
    
    /**
     * Adds an alarm to a user.
     * @param user The user you want to link the alarm to.
     * @param alarm The alarm you want to link to the user.
     * @return A User with the new alarm.
     * @throws DatabaseException when the user or the alarm is null.
     */
    public User addAlarmToUser(User user, Alarm alarm) throws DatabaseException;
    
    /**
     * Adds an alarm to a user, based on their ID's.
     * @param userId The ID of the user you want to link to the alarm.
     * @param alarmId The ID of the alarm you want to link to the user.
     * @return An updated User object that's linked to the alarm object.
     * @throws DatabaseException when one of the IDs is wrong.
     */
    public User addAlarmToUser(int userId, int alarmId) throws DatabaseException;
    
    /**
     * Removes an alarm from a user.
     * @param user The user whose alarm you want to remove.
     * @param alarm The alarm you want to remove from the user.
     * @return An updated user object that's not linked to the alarm.
     * @throws DatabaseException If the user or the alarm is null.
     */
    public User removeAlarmFromUser(User user, Alarm alarm) throws DatabaseException;
    
    /**
     * Removes an alarm from a user based on their IDs.
     * @param userId The userID from the User whose alarm you want to unlink.
     * @param alarmId The alarmID from the Alarm whose user you want to unlink.
     * @return An updated user object that's not linked to the alarm.
     * @throws DatabaseException when one of the IDs is wrong.
     */
    public User removeAlarmFromUser(int userId, int alarmId) throws DatabaseException;
    
    // *******************************
    // *CLEANUP METHODS*
    // *******************************
    
    /**
     * Searches the database for alarms that have already happened and deletes them.
     * @throws DatabaseException 
     */
    public void cleanUpAlarms() throws DatabaseException;
    
    /**
     * Closes the connection.
     */
    public void closeConnection();
    
    
}
