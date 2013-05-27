/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.entities.ServerResult;
import db.GroupDB;
import exceptions.DatabaseException;
import exceptions.GCMException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        ServerResult<String[]> result = new ServerResult<String[]>();
        try {
            result.setData((String[]) groupDB.getTagsFromUser(gcmID).toArray());
        } catch (DatabaseException ex) {
            result.setException(ex);
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
            groupDB.subscribeUserToTag(gcmID, tag);
        } catch (DatabaseException ex) {
            result.setException(ex);
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
            result.setException(ex);
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return result;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sendContactsToGroup")
    public ServerResult sendContactsToGroup(@WebParam(name = "tag") String tag, @WebParam(name = "contacts") List<Contact> contacts) {
        ServerResult result = new ServerResult();
        try {
            List<String> gcmIDs = new ArrayList<String>();
            gcmIDs.addAll(groupDB.getUsersFromTag(tag));
            ContactSender.sendContactsToGroup(gcmIDs, contacts);
        } catch (GCMException ex) {
            result.setException(ex);
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
        ServerResult<Boolean> result = new ServerResult<Boolean>();
        try {
            result.setData(groupDB.tagExists(tag));
        } catch (DatabaseException ex) {
            result.setException(ex);
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
            result.setException(ex);
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            return result;
        }
    }
}
