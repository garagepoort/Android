package be.cegeka.memento.domain.futuretasks;

import static be.cegeka.memento.domain.infrastructure.PropertyReader.getProperty;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import be.cegeka.android.flibture.FutureTask;
import be.cegeka.memento.domain.exception.TechnicalException;
import be.cegeka.memento.entities.Contact;

import com.google.gson.Gson;

public class SendContactsTask extends FutureTask<Void, Object> {
	private Context context;

	public SendContactsTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected Void doInBackgroundFuture(Object... arguments) throws Exception {
		try {
			SoapObject response = null;
			String tag = (String) arguments[0];
			@SuppressWarnings("unchecked")
			ArrayList<Contact> contacts = (ArrayList<Contact>) arguments[1];
			String contactsString = new Gson().toJson(contacts);
			response = getSendContactsResponse(contactsString, tag);
			handleResponse(response);
			return null;
		} catch (Exception exception) {
			throw new TechnicalException(exception);
		}
	}

	private void handleResponse(SoapObject response) throws Exception {
		SoapObject so = (SoapObject) response.getProperty("return");
		String message = so.getPropertySafelyAsString("exceptionMessage");
		if (message != null && !message.equals("")) {
			throw new Exception(message);
		}
	}

	private SoapObject getSendContactsResponse(String contacts, String tag) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "SEND_CONTACTS_TO_GROUP"));
		request.addProperty("contacts", contacts);
		request.addProperty("tag", tag);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "config.properties", "URL"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "SEND_CONTACTS_TO_GROUP"), envelope);
		response = (SoapObject) envelope.bodyIn;
		return response;
	}
}
