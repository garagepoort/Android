package be.cegeka.memento.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.memento.domain.events.ContactListUpdatedEvent;
import be.cegeka.memento.entities.Contact;

public class ContactsModel extends Tapper {

	private static final ContactsModel INSTANCE = new ContactsModel();
	private List<Contact> contacts = new ArrayList<Contact>();

	public static ContactsModel getInstance() {
		return INSTANCE;
	}

	private ContactsModel() {
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
		ContactListUpdatedEvent event = new ContactListUpdatedEvent();
		event.setData(contacts);
		tapShoulders(event);
	}

	public List<Contact> getContacts() {
		return Collections.unmodifiableList(contacts);
	}

	public void addContact(Contact contact) {
		if (!contacts.contains(contact)) {
			contacts.add(contact);
			ContactListUpdatedEvent event = new ContactListUpdatedEvent();
			event.setData(contacts);
			tapShoulders(event);
		}
	}
}
