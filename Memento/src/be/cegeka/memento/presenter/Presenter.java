package be.cegeka.memento.presenter;

import static be.cegeka.android.flibture.Future.whenResolved;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;
import be.cegeka.memento.domain.events.ErrorEvent;
import be.cegeka.memento.domain.events.SendContactsEvent;
import be.cegeka.memento.domain.events.TagEvent;
import be.cegeka.memento.domain.futuretasks.AddTagTask;
import be.cegeka.memento.domain.futuretasks.GetTagFromUserTask;
import be.cegeka.memento.domain.futuretasks.SendContactsTask;
import be.cegeka.memento.domain.futuretasks.SubscribeToTagTask;
import be.cegeka.memento.domain.futuretasks.UnsubscribeFromTagTask;
import be.cegeka.memento.domain.utilities.ContactsPersistence;
import be.cegeka.memento.domain.utilities.Group;
import be.cegeka.memento.domain.utilities.PersonalContactSaver;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;
import be.cegeka.memento.model.ContactsModel;
import be.cegeka.memento.model.TagsModel;
import be.cegeka.memento.view.Toast;

import com.google.android.gcm.GCMRegistrar;


public class Presenter extends Tapper
{




	public void saveContact(Context context, Contact contact)
	{
		try {
			new PersonalContactSaver(context).saveContact(contact);
		} catch (IOException e) {
			ErrorEvent errorEvent = new ErrorEvent();
			errorEvent.setData(e);
			tapShoulders(errorEvent);
		}
	}


	public void getContacts(Context context) throws ContactException
	{
		List <Contact> contacts = new ContactsPersistence(context).loadContacts();
		contacts.add(0, new PersonalContactSaver(context).loadContact());
		ContactsModel.getInstance().setContacts(contacts);
	}


	public void getTags(final Context context)
	{
		String gcmID = GCMRegistrar.getRegistrationId(context);
		GetTagFromUserTask fromUserTask= new GetTagFromUserTask(context);
		Future<ArrayList<Group>> future = fromUserTask.executeFuture(gcmID);
		Future.whenResolved(future, new FutureCallable<ArrayList<Group>>() {

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onSucces(ArrayList<Group> tags) {
				TagsModel.getInstance().setTags(tags);
				Toast.showBlueToast(context, tags.toString());	
			}
		});
	}


	public Contact getPersoonlijkContact(Context context)
	{
		return new PersonalContactSaver(context).loadContact();
	}


	public void sendContacts(List<Contact> checkedContacts, String tag, Context context) throws ContactException
	{
		for(Contact c : checkedContacts){
			
			Cursor mailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + String.valueOf(c.getId()), null, null);
			while (mailCur.moveToNext()) {
				c.setEmail(mailCur.getString(mailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
			}
			mailCur.close();
			
			Cursor numberCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + String.valueOf(c.getId()), null, null);
			while (numberCur.moveToNext()) {
				c.setTel(numberCur.getString(numberCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			}
			numberCur.close();
		}
		
		Future<Void> future = new SendContactsTask(context).executeFuture(tag, checkedContacts);
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
			public void onSucces(Void ontvangers)
			{
				SendContactsEvent contactsEvent = new SendContactsEvent();
				tapShoulders(contactsEvent);
			}
		});
	}


	public void addTag(final String tag, Context context)
	{
		Future<Void> future = new AddTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
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
				TagEvent event = new TagEvent();
				event.setData("Tag succesvol toegevoegd.");
				tapShoulders(event);
			}
		});
	}


	public void addToTag(Context context, String tag)
	{
		Future<Integer> future = new SubscribeToTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
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


	public void deleteTag(String tag, final Context context)
	{
		Future<Void> future = new UnsubscribeFromTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
		whenResolved(future, new FutureCallable<Void>() {

			@Override
			public void onError(Exception exception) {
				ErrorEvent errorEvent = new ErrorEvent();
				errorEvent.setData(exception);
				tapShoulders(errorEvent);
			}

			@Override
			public void onSucces(Void arg0) {
				getTags(context);
			}
		});
	}

}
