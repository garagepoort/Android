package be.cegeka.memento.events;

import java.util.Collections;
import java.util.List;

import be.cegeka.android.ShouldrTap.AbstractEvent;
import be.cegeka.memento.entities.Contact;

public class ContactListUpdatedEvent extends AbstractEvent<List<Contact>> {

	List<Contact> contacts;
	
	@Override
	public List<Contact> getData() {
		return Collections.unmodifiableList(contacts);
	}

	@Override
	public void setData(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
