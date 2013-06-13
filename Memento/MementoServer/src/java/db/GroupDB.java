/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import exceptions.DatabaseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author ivarv
 */
public class GroupDB {

    private static HashMap<String, HashSet<String>> groups;
    private static GroupDB instance = new GroupDB();
    
    public static final String TAG_NULL_MESSAGE = "Tag mag niet null zijn.";
    public static final String GCMID_NULL_MESSAGE = "GcmID mag niet null zijn.";
    public static final String TAG_NONEXISTENT_MESSAGE = "Tag bestaat niet.";
    public static final String TAG_EXISTS_MESSAGE = "Tag bestaat al.";

    private GroupDB() {
        groups = new HashMap<String, HashSet<String>>();
    }

    public static GroupDB getInstance() {
        return instance;
    }

    public HashSet<String> getUsersFromTag(String tag) throws DatabaseException {
        if (tag == null) {
            return new HashSet<String>();
        }
        tag = tag.toLowerCase();
        if(!tagExists(tag)){
            throw new DatabaseException(TAG_NONEXISTENT_MESSAGE);
        }
        return groups.get(tag);
    }

    public Map<String, Integer> getTagsFromUser(String gcmID) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException(GCMID_NULL_MESSAGE);
        }
        Map<String, Integer> tags = new HashMap<String, Integer>();
        for (Entry<String, HashSet<String>> e : groups.entrySet()) {
            if (e.getValue().contains(gcmID)) {
                tags.put(e.getKey(), e.getValue().size());
            }
        }
        return tags;
    }
    
    public int numberOfSubscribers(String tag) throws DatabaseException{
        if(tag == null){
            throw new DatabaseException(TAG_NULL_MESSAGE);
        }
        tag = tag.toLowerCase();
        if(!tagExists(tag)){
            throw new DatabaseException(TAG_NONEXISTENT_MESSAGE);
        }
        return groups.get(tag).size();
    }

    public int subscribeUserToTag(String gcmID, String tag) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException(GCMID_NULL_MESSAGE);
        }
        if(tag == null){
            throw new DatabaseException(TAG_NULL_MESSAGE);
        }
        tag = tag.toLowerCase();
        if(!tagExists(tag)){
            throw new DatabaseException(TAG_NONEXISTENT_MESSAGE);
        }
        groups.get(tag).add(gcmID);
        return groups.get(tag).size();
    }

    public void unsubscribeUserFromTag(String gcmID, String tag) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException(GCMID_NULL_MESSAGE);
        }
        if(tag == null){
            throw new DatabaseException(TAG_NULL_MESSAGE);
        }
        tag = tag.toLowerCase();
        if (groups.containsKey(tag)) {
            if (groups.get(tag).size() == 1) {
                groups.remove(tag);
            } else {
                groups.get(tag).remove(gcmID);
            }
        }
    }
    
    public Set<String> getAllTags(){
        return groups.keySet();
    }
    
    public boolean tagExists(String tag) throws DatabaseException{
        if(tag == null){
            throw new DatabaseException(TAG_NULL_MESSAGE);
        }
        tag = tag.toLowerCase();
        return groups.containsKey(tag);
    }
    
    public void createTag(String gcmID, String tag) throws DatabaseException{
        tag = tag.toLowerCase();
        if(tagExists(tag)){
            throw new DatabaseException(TAG_EXISTS_MESSAGE);
        }
        groups.put(tag, new HashSet<String>());
        subscribeUserToTag(gcmID, tag);
    }
}
