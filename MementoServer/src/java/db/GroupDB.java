/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import exceptions.DatabaseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

    private GroupDB() {
        groups = new HashMap<String, HashSet<String>>();
    }

    public static GroupDB getInstance() {
        return instance;
    }

    public HashSet<String> getUsersFromTag(String tag) {
        if (tag == null) {
            return new HashSet<String>();
        }
        tag = tag.toLowerCase();
        return groups.get(tag);
    }

    public List<String> getTagsFromUser(String gcmID) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException("GcmID can't be null.");
        }
        List<String> tags = new LinkedList<String>();
        for (Entry<String, HashSet<String>> e : groups.entrySet()) {
            if (e.getValue().contains(gcmID)) {
                tags.add(e.getKey());
            }
        }
        return tags;
    }

    public void subscribeUserToTag(String gcmID, String tag) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException("GcmID can't be null.");
        }
        if(tag == null){
            throw new DatabaseException("Tag can't be null.");
        }
        tag = tag.toLowerCase();
        if(!tagExists(tag)){
            throw new DatabaseException("Tag doesn't exist.");
        }
        groups.get(tag).add(gcmID);
    }

    public void unsubscribeUserFromTag(String gcmID, String tag) throws DatabaseException {
        if(gcmID == null){
            throw new DatabaseException("GcmID can't be null.");
        }
        if(tag == null){
            throw new DatabaseException("Tag can't be null.");
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
            throw new DatabaseException("Tag can't be null.");
        }
        tag = tag.toLowerCase();
        return groups.containsKey(tag);
    }
    
    public void createTag(String gcmID, String tag) throws DatabaseException{
        tag = tag.toLowerCase();
        if(tagExists(tag)){
            throw new DatabaseException("Tag already exists.");
        }
        groups.put(tag, new HashSet<String>());
        subscribeUserToTag(gcmID, tag);
    }
}
