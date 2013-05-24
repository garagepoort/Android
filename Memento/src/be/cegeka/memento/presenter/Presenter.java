package be.cegeka.memento.presenter;

import static be.cegeka.android.flibture.Future.whenResolved;
import java.util.List;
import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;
import be.cegeka.memento.domain.DB;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.events.ErrorEvent;
import be.cegeka.memento.events.SendContactsEvent;
import be.cegeka.memento.events.TagEvent;
import be.cegeka.memento.futuretasks.AddTagTask;
import be.cegeka.memento.futuretasks.AddToTagTask;
import be.cegeka.memento.futuretasks.SendContactsTask;


public class Presenter extends Tapper
{
	private DB db;


	public Presenter()
	{
		this.db = new DB();
	}


	public void saveContact(Contact contact, boolean persoonlijkContact)
	{
		db.saveContact(contact, persoonlijkContact);
	}


	public void getContacts()
	{
		db.getContacts();
	}


	public void getTags()
	{
		// TODO server
		db.getTags();
	}


	public Contact getPersoonlijkContact()
	{
		return db.getPersoonlijkContact();
	}


	public void sendContacts(List<Contact> checkedContacts, String tag)
	{
		Future<Integer> future = new SendContactsTask().executeFuture(new Object());
		whenResolved(future, new FutureCallable<Integer>()
		{
			@Override
			public void onError(Exception exception)
			{
				ErrorEvent errorEvent = new ErrorEvent();
				errorEvent.setData(exception);
				tapShoulders(errorEvent);
			}


			@Override
			public void onSucces(Integer ontvangers)
			{
				SendContactsEvent contactsEvent = new SendContactsEvent();
				contactsEvent.setData(ontvangers);
				tapShoulders(contactsEvent);
			}
		});
	}


	public void addTag(final String input)
	{
		Future<Void> future = new AddTagTask().executeFuture(input);
		whenResolved(future, new FutureCallable<Void>()
		{
			@Override
			public void onError(Exception exception)
			{
				ErrorEvent errorEvent = new ErrorEvent();
				errorEvent.setData(exception);
				tapShoulders(errorEvent);
			}


			@Override
			public void onSucces(Void arg0)
			{
				db.tags.add(input);
				TagEvent event = new TagEvent();
				event.setData("Tag succesvol toegevoegd.");
				tapShoulders(event);
			}
		});
	}


	public void addToTag(String input)
	{
		Future<Integer> future = new AddToTagTask().executeFuture(input);
		whenResolved(future, new FutureCallable<Integer>()
		{
			@Override
			public void onError(Exception exception)
			{
				ErrorEvent errorEvent = new ErrorEvent();
				errorEvent.setData(exception);
				tapShoulders(errorEvent);
			}


			@Override
			public void onSucces(Integer integer)
			{
				TagEvent event = new TagEvent();
				event.setData("Succesvol toegevoegd aan tag met " + integer + " leden.");
				tapShoulders(event);
			}
		});
	}


	public void deleteTag(String tag)
	{
		DB.tags.remove(tag);
		getTags();
	}


	public void deleteContact(Contact contact)
	{
		DB.list.remove(contact);
		getContacts();
	}
}
