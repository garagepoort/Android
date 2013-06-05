package be.cegeka.memento.facade;

import static be.cegeka.android.flibture.Future.whenResolved;
import static be.cegeka.memento.domain.utilities.PropertyReader.getProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.events.ErrorEvent;
import be.cegeka.memento.domain.events.SendContactsEvent;
import be.cegeka.memento.domain.events.TagEvent;
import be.cegeka.memento.domain.exception.TechnicalException;
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
import com.google.android.gcm.GCMRegistrar;
import com.google.zxing.integration.android.IntentIntegrator;


public class Facade extends Tapper
{
	private Context context;


	public Facade(Context context)
	{
		this.context = context;
	}


	public void saveContact(Contact contact)
	{
		try
		{
			new PersonalContactSaver(context).saveContact(contact);
		}
		catch (IOException exception)
		{
			handleError(new TechnicalException(getProperty(context, "exceptions.properties", "SAVE_CONTACT")));
		}
	}


	public void getContacts() throws ContactException
	{
		List<Contact> contacts = new ContactsPersistence(context).loadContacts();
		contacts.add(0, new PersonalContactSaver(context).loadContact());
		ContactsModel.getInstance().setContacts(contacts);
	}


	public void getTags()
	{
		String gcmID = GCMRegistrar.getRegistrationId(context);
		GetTagFromUserTask fromUserTask = new GetTagFromUserTask(context);
		Future<ArrayList<Group>> future = fromUserTask.executeFuture(gcmID);
		Future.whenResolved(future, new FutureCallable<ArrayList<Group>>()
		{
			@Override
			public void onError(Exception exception)
			{
				if (exception instanceof TechnicalException)
				{
					handleError(exception);
				}
				else
				{
					handleError(new TechnicalException(getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION")));
				}
			}


			@Override
			public void onSucces(ArrayList<Group> tags)
			{
				TagsModel.getInstance().setTags(tags);
			}
		});
	}


	public Contact getPersoonlijkContact()
	{
		return new PersonalContactSaver(context).loadContact();
	}


	@SuppressLint("InlinedApi")
	public void sendContacts(List<Contact> checkedContacts, String tag) throws ContactException
	{
		ContactsPersistence contactsPersistence = new ContactsPersistence(context);
		checkedContacts = contactsPersistence.completeContacts(checkedContacts);
		Future<Void> future = new SendContactsTask(context).executeFuture(tag, checkedContacts);
		whenResolved(future, new FutureCallable<Void>()
		{
			@Override
			public void onError(Exception exception)
			{
				if (exception instanceof TechnicalException)
				{
					handleError(exception);
				}
				else
				{
					handleError(new TechnicalException(getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION")));
				}
			}


			@Override
			public void onSucces(Void voiid)
			{
				SendContactsEvent contactsEvent = new SendContactsEvent();
				tapShoulders(contactsEvent);
			}
		});
	}


	public void addTag(final String tag)
	{
		Future<Void> future = new AddTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
		whenResolved(future, new FutureCallable<Void>()
		{
			@Override
			public void onError(Exception exception)
			{
				if (exception instanceof TechnicalException)
				{
					handleError(exception);
				}
				else
				{
					handleError(new TechnicalException(getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION")));
				}
			}


			@Override
			public void onSucces(Void arg0)
			{
				TagEvent event = new TagEvent();
				event.setData(context.getString(R.string.result_tag_successful_added));
				tapShoulders(event);
			}
		});
	}


	public void addToTag(String tag)
	{
		Future<Integer> future = new SubscribeToTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
		whenResolved(future, new FutureCallable<Integer>()
		{
			@Override
			public void onError(Exception exception)
			{
				if (exception instanceof TechnicalException)
				{
					handleError(exception);
				}
				else
				{
					handleError(new TechnicalException(getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION")));
				}
			}


			@Override
			public void onSucces(Integer integer)
			{
				TagEvent event = new TagEvent();
				String part_2 = integer == 1 ? context.getString(R.string.toast_added_to_tag_part_2_single) : context.getString(R.string.toast_added_to_tag_part_2_plural);
				event.setData(context.getString(R.string.toast_added_to_tag_part_1) + " " + integer + " " + part_2);
				tapShoulders(event);
			}
		});
	}


	public void deleteTag(String tag)
	{
		Future<Void> future = new UnsubscribeFromTagTask(context).executeFuture(GCMRegistrar.getRegistrationId(context), tag);
		whenResolved(future, new FutureCallable<Void>()
		{
			@Override
			public void onError(Exception exception)
			{
				if (exception instanceof TechnicalException)
				{
					handleError(exception);
				}
				else
				{
					handleError(new TechnicalException(getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION")));
				}
			}


			@Override
			public void onSucces(Void arg0)
			{
				getTags();
			}
		});
	}


	public boolean isValidTag(String tag)
	{
		return tag.matches("^[a-zA-Z0-9_]+$");
	}


	public boolean isValidTagForSending(String tag)
	{
		return tag.matches("^[a-zA-Z0-9_ \\(\\)]+$");
	}


	private void handleError(Exception exception)
	{
		ErrorEvent errorEvent = new ErrorEvent();
		errorEvent.setData(exception);
		tapShoulders(errorEvent);
	}


	public void openScanner(Activity activity)
	{
		IntentIntegrator integrator = new IntentIntegrator(activity);
		integrator.initiateScan();
	}

}
