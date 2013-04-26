package be.cegeka.android.alarms.domain.model;

import be.cegeka.android.alarms.domain.entities.User;
import be.cegeka.android.alarms.domain.exceptions.BusinessException;
import be.cegeka.android.alarms.infrastructure.DatabaseException;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GCMCommunication
{
    private final String API_KEY = "AIzaSyC3uJTO9Pp4qrvd3Aoa4NJc0zkeNJIDqmM";
    private final String COLLAPSE_KEY = "COLLAPSE_KEY";
    private final Service service;


    public GCMCommunication(Service service)
    {
        this.service = service;
    }


    public void registerUser(User user, String resistrationId) throws BusinessException
    {
        user.setGCMid(resistrationId);
        try
        {
            service.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }
    
    
    public void unregisterUser(User user) throws BusinessException
    {
        user.setGCMid(null);
        try
        {
            service.updateUser(user);
        }
        catch (DatabaseException ex)
        {
            throw new BusinessException(ex);
        }
    }


    public void notifyUserOfChange(User user) throws BusinessException
    {
        String registrationId = user.getGCMid();
        List<String> registrationIds = new ArrayList<>(1);
        registrationIds.add(registrationId);

        Sender sender = new Sender(API_KEY);
        Message.Builder builder = new Message.Builder();
        builder.collapseKey(COLLAPSE_KEY);
        Message message = builder.build();

        try
        {
            MulticastResult multicastResult = sender.send(message, registrationIds, 5);

            for (Result result : multicastResult.getResults())
            {
                if (result.getMessageId() != null)
                {
                    String canonicalRegId = result.getCanonicalRegistrationId();
                    if (canonicalRegId != null)
                    {
                        registerUser(user, canonicalRegId);
                    }
                }
                else
                {
                    String error = result.getErrorCodeName();
                    if (error.equals(Constants.ERROR_NOT_REGISTERED))
                    {
                        unregisterUser(user);
                    }
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(GCMCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


