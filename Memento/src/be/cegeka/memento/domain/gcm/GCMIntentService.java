package be.cegeka.memento.domain.gcm;

import java.io.IOException;
import android.content.Context;
import android.content.Intent;
import be.cegeka.memento.domain.utilities.ContactsPersistence;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.model.ContactsModel;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;


public class GCMIntentService extends GCMBaseIntentService
{

	public GCMIntentService()
	{
		super("362183860979");
	}


	@Override
	protected void onError(Context arg0, String arg1)
	{
		System.out.println("ERROR: " + arg1);
	}


	@Override
	protected void onMessage(final Context context, Intent intent)
	{
		System.out.println("MESSAGE RECEIVED");
		String json_info = intent.getExtras().getString("CONTACT");
		System.out.println(json_info);
		Contact contact = (Contact) new Gson().fromJson(json_info, Contact.class);
		if (contact != null)
		{
			try
			{
				if (!ContactsModel.getInstance().getContacts().contains(contact))
				{
					new ContactsPersistence(context).saveContact(contact);
					ContactsModel.getInstance().addContact(contact);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			System.out.println(contact);
		}
	}


	@Override
	protected void onRegistered(final Context arg0, String gcmid)
	{
		System.out.println("REGISTERED WITH GCM!!!!");
	}


	@Override
	protected void onUnregistered(final Context context, String registrationID)
	{
		System.out.println("UNREGISTERED");
	}
}
