package be.cegeka.alarms.android.client.domain.login;

import static be.cegeka.alarms.android.client.domain.infrastructure.PropertyReader.getProperty;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import be.cegeka.alarms.android.client.domain.exception.TechnicalException;
import be.cegeka.android.alarms.transferobjects.ServerResult;
import be.cegeka.android.alarms.transferobjects.UserTO;
import be.cegeka.android.flibture.FutureTask;

public class LoginUserTask extends FutureTask<UserTO, String> {

	private Context context;
	public LoginUserTask(Context context) {
		this.context = context;
	}

	@Override
	protected UserTO doInBackgroundFuture(String... uri) throws TechnicalException {
		UserTO userTO = null;
		SoapObject response = null;
		String email = uri[0];
		String paswoord = uri[1];
		try {
			response = getUserResponse(email, paswoord);
		} catch (Exception exception) {
			throw new TechnicalException(getProperty(context, "exceptions.properties", "OTHER"));
		}
		userTO = getUser(response);
		return userTO;
	}

	/**
	 * Connects to the web service asking the web service for a user with this
	 * login credentials.
	 * 
	 * @param username
	 *            The username.
	 * @param paswoord
	 *            The password of the {@link User}.
	 * @return A soapobject if the login credentials were correct or null of
	 *         they were incorrect.
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private SoapObject getUserResponse(String username, String paswoord) throws IOException, XmlPullParserException, SocketTimeoutException {
		System.out.println("GETUSERRESPONSE");
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "LOGIN"));
		request.addProperty("username", username);
		request.addProperty("paswoord", paswoord);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		SoapObject response = null;
		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "config.properties", "URL"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "LOGIN"), envelope);
		response = (SoapObject) envelope.getResponse();
		return response;
	}

	/**
	 * 
	 * Make a {@link User} object from the response from the server.
	 * 
	 * @param response
	 */
	private UserTO getUser(SoapObject response) throws TechnicalException {
		System.out.println(response.getPropertySafelyAsString("error"));
		ServerResult result = ServerResult.valueOf(response.getPropertySafelyAsString("error"));
		if (result == ServerResult.SUCCESS) {
			SoapObject userto = (SoapObject) response.getProperty("userTO");
			String achternaam = userto.getPropertySafelyAsString("achternaam").toString();
			String email = userto.getPropertySafelyAsString("email").toString();
			int id = Integer.parseInt(userto.getPropertySafelyAsString("userid").toString());
			String naam = userto.getPropertySafelyAsString("naam").toString();
			boolean admin = Boolean.getBoolean(userto.getPropertySafelyAsString("admin").toString());
			String gcmID = userto.getPropertySafelyAsString("GCMid");
			return new UserTO(id, naam, achternaam, email, admin, gcmID);
		} else if (result == ServerResult.WRONG_USER_CREDENTIALS) {
			throw new TechnicalException(getProperty(context, "exceptions.properties", "CREDENTIALS"));
		} else {
			throw new TechnicalException(getProperty(context, "exceptions.properties", "OTHER"));
		}
	}
}
