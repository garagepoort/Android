/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.entities.ServerResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.GroupDB;
import exceptions.DatabaseException;
import exceptions.GCMException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import messaging.ContactSender;

/**
 *
 * @author ivarv
 */
@WebService(serviceName = "MementoWebService")
public class MementoWebService {

    private GroupDB groupDB = GroupDB.getInstance();

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTagsFromUser")
    public ServerResult getTagsFromUser(@WebParam(name = "gcmID") String gcmID) {
        ServerResult result = new ServerResult();
        try {
            String tagsInJson = new Gson().toJson(groupDB.getTagsFromUser(gcmID));
            result.setData(tagsInJson);
        } catch (Exception ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "subscribeToTag")
    public ServerResult subscribeToTag(@WebParam(name = "gcmID") String gcmID, @WebParam(name = "tag") String tag) {
        ServerResult result = new ServerResult();
        try {
            String numberOfSubscribersInJson = new Gson().toJson(groupDB.subscribeUserToTag(gcmID, tag));
            result.setData(numberOfSubscribersInJson);
        } catch (DatabaseException ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "unsubscribeFromTag")
    public ServerResult unsubscribeFromTag(@WebParam(name = "gcmID") String gcmID, @WebParam(name = "tag") String tag) {
        ServerResult result = new ServerResult();
        try {
            groupDB.unsubscribeUserFromTag(gcmID, tag);
        } catch (DatabaseException ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sendContactsToGroup")
    public ServerResult sendContactsToGroup(@WebParam(name = "tag") String tag, @WebParam(name = "contacts") String contacts) {
        ServerResult result = new ServerResult();
        try {
            List<String> gcmIDs = new ArrayList<String>();
            gcmIDs.addAll(groupDB.getUsersFromTag(tag));
            Type listOfContacts = new TypeToken<List<Contact>>() {}.getType();
            List<Contact> contactsList = new Gson().fromJson(contacts, listOfContacts);
            new ContactSender().sendContactsToGroup(gcmIDs, contactsList);
        } catch (Exception ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "tagExists")
    public ServerResult tagExists(@WebParam(name = "tag") String tag) {
        ServerResult result = new ServerResult();
        try {
            String jsonBoolean = new Gson().toJson(groupDB.tagExists(tag));
            result.setData(jsonBoolean);
        } catch (DatabaseException ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createTag")
    public ServerResult createTag(@WebParam(name = "gcmID") String gcmID, @WebParam(name = "tag") String tag) {
        ServerResult result = new ServerResult();
        try {
            groupDB.createTag(gcmID, tag);
        } catch (DatabaseException ex) {
            result.setExceptionMessage(ex.getMessage());
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }
}
