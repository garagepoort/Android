package messaging;

import be.cegeka.memento.entities.Contact;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import exceptions.GCMException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import webservice.MementoWebService;

public class ContactSender {

    private final String API_KEY = "AIzaSyBU-BtejcIzYmBtQANmXFCO981hyrK85iI";
    private final String CONTACT_DATA_KEY = "CONTACT";

    public void sendContactsToGroup(List<String> gcmIDs, List<Contact> contacts) throws GCMException {
        if (gcmIDs == null) {
            throw new GCMException("GcmIds lijst mag niet null zijn.");
        }
        if (gcmIDs.isEmpty()) {
            throw new GCMException("GCMIds lijst mag niet leeg zijn.");
        }
        if (contacts == null) {
            throw new GCMException("Contacts lijst mag niet null zijn.");
        }
        if (contacts.isEmpty()) {
            throw new GCMException("De lijst van contacten mag niet leeg zijn.");
        }
        try {
            Sender sender = new Sender(API_KEY);
            for (Contact c : contacts) {
                Message.Builder builder = new Message.Builder();
                builder.addData(CONTACT_DATA_KEY, new Gson().toJson(c));
                Message message = builder.build();
                sender.send(message, gcmIDs, 5);
            }
        } catch (Exception ex) {
            Logger.getLogger(MementoWebService.class.getName()).log(Level.SEVERE, null, ex);
            throw new GCMException(ex);
        }
    }
}
