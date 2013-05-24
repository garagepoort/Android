package be.cegeka.memento.presenter;

import be.cegeka.memento.domain.DB;
import be.cegeka.memento.entities.Contact;

public class Presenter {
	
	private DB db;
	
	public Presenter() {
		this.db = new DB();
	}

	public void saveContact(Contact contact, boolean persoonlijkContact){
		db.saveContact(contact, persoonlijkContact);
	}
	
	public void getContacts(){
		db.getContacts();
	}

	public void getTags(){
		db.getTags();
	}
	
	public Contact getPersoonlijkContact(){
		return db.getPersoonlijkContact();
	}
}
