/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import exceptions.DatabaseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ivarv
 */
public class GroupDBTest {

    private GroupDB db;
    private String tag = "presentatiex";
    private String tag2 = "presentatiey";
    private String user1 = "randomGCMID1";
    private String user2 = "randomGCMID2";
    private String user3 = "randomGCMID3";
    private String user4 = "randomGCMID4";

    public GroupDBTest() {
        db = GroupDB.getInstance();
    }

    @Before
    public void setUp() throws DatabaseException {
        db.subscribeUserToTag(user1, tag);
        db.subscribeUserToTag(user2, tag);
        db.subscribeUserToTag(user3, tag);
        db.subscribeUserToTag(user4, tag);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.unsubscribeUserFromTag(user1, tag);
        db.unsubscribeUserFromTag(user2, tag);
        db.unsubscribeUserFromTag(user3, tag);
        db.unsubscribeUserFromTag(user4, tag);
    }

    @Test
    public void testGetUsersFromTag() throws DatabaseException {
        Set<String> users = db.getUsersFromTag(tag);
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
        assertTrue(users.contains(user4));
    }

    @Test
    public void testGetTagsFromUser() throws Exception {
        db.subscribeUserToTag(user1, tag2);
        List<String> tags = db.getTagsFromUser(user1);
        assertTrue(tags.contains(tag));
        assertTrue(tags.contains(tag2));
    }

    @Test
    public void testUnsubscribeUserFromTag() throws Exception {
        assertTrue(db.getTagsFromUser(user1).contains(tag));
        db.unsubscribeUserFromTag(user1, tag);
        assertFalse(db.getTagsFromUser(user1).contains(tag));
    }

    @Test
    public void testGetAllTags() throws DatabaseException {
        db.subscribeUserToTag(user1, tag2);
        Set<String> tags = db.getAllTags();
        assertTrue(tags.contains(tag));
        assertTrue(tags.contains(tag2));
    }
    
}