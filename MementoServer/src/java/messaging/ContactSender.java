package messaging;

import be.cegeka.memento.entities.Contact;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import exceptions.GCMException;
import java.io.IOException;
import java.util.List;

public class ContactSender {

    private static final String API_KEY = "AIzaSyBU-BtejcIzYmBtQANmXFCO981hyrK85iI";
    private static final String COLLAPSE_KEY = "COLLAPSE_KEY";
    private static final String CONTACT_DATA_KEY = "CONTACT";

    public static void sendContactsToGroup(List<String> gcmIDs, List<Contact> contacts) throws GCMException {
        Sender sender = new Sender(ContactSender.API_KEY);
        Gson gSon = new Gson();
        for (Contact c : contacts) {
            try {
                Message.Builder builder = new Message.Builder();
                builder.collapseKey(ContactSender.COLLAPSE_KEY);
                builder.addData(ContactSender.CONTACT_DATA_KEY, gSon.toJson(c));
                Message message = builder.build();
                sender.send(message, gcmIDs, 5);
            } catch (IOException ex) {
                throw new GCMException(ex);
            }
        }
    }
}
