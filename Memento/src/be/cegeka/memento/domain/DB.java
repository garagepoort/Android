package be.cegeka.memento.domain;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;
import be.cegeka.memento.model.ContactsModel;
import be.cegeka.memento.model.TagsModel;

public class DB {

	private static List<Contact> list;
	private static List<String> tags;
	private static Contact persoonlijkContactDetails;

	public DB() {
		if (list == null) {
			list = new ArrayList<Contact>();
			try {
				Contact contact1 = new Contact("Maes", "David", "d.s.maes@gmail.com", "016777888");
				contact1.setId(1);
				Contact contact2 = new Contact("VH", "Ivar", "ivar@gmail.com", "016254387");
				contact2.setId(2);
				Contact contact3 = new Contact("Crombez", "Hannes", "@gmail.com", "016389742");
				contact3.setId(3);

				list.add(contact1);
				list.add(contact2);
				list.add(contact3);

			} catch (ContactException e) {
				e.printStackTrace();
			}
		}

		if (tags == null) {
			tags = new ArrayList<String>();
			tags.add("Cegeka");
			tags.add("Stage");
			tags.add("Android");
		}
		
		if(persoonlijkContactDetails == null){
			try {
				persoonlijkContactDetails = new Contact("Crombez", "Hannes", "h.cr@me.com", "016548632");
				persoonlijkContactDetails.setId(1000);
			} catch (ContactException e) {
				e.printStackTrace();
			}
		}
	}

	public void getContacts() {
		ContactsModel.getInstance().setContacts(list);
	}

	public void getTags() {
		TagsModel.getInstance().setTags(tags);
	}
	
	public Contact getPersoonlijkContact(){
		return persoonlijkContactDetails;
	}

	public void saveContact(Contact contact, boolean persoonlijkContact) {
		if(persoonlijkContact){
			persoonlijkContactDetails = contact;
		}
		
		else if (contact.getId() != 0) {
			for (Contact listContact : list) {
				if (listContact.getId() == contact.getId()) {
					list.remove(listContact);
					break;
				}
			}
		}

		list.add(contact);
		ContactsModel.getInstance().setContacts(list);
	}
}
