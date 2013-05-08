package be.cegeka.alarms.android.client.serverconnection.login;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import be.cegeka.alarms.android.client.exception.TechnicalException;
import be.cegeka.alarms.android.client.futureimplementation.FutureTask;
import be.cegeka.alarms.android.client.futureimplementation.exception.FutureException;
import be.cegeka.alarms.android.client.serverconnection.ServerUtilities;
import be.cegeka.android.alarms.transferobjects.ServerResult;
import be.cegeka.android.alarms.transferobjects.UserTO;


public class LoginUserTask extends FutureTask<UserTO, String>
{



	@Override
	protected UserTO doInBackgroundFuture(String... uri) throws FutureException
	{
		UserTO userTO = null;
		SoapObject response = null;
		try
		{
			String email = uri[0];
			String paswoord = uri[1];
			response = getUserResponse(email, paswoord);
		}
		catch (IOException e)
		{
			throw new FutureException("Timed out");
		}
		catch (XmlPullParserException e)
		{
			throw new FutureException("An error has occurred");
		}
		userTO = getUser(response);
		return userTO;
	}


	@Override
	protected void onPostExecuteFuture(UserTO result)
	{
		
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
	private SoapObject getUserResponse(String username, String paswoord) throws IOException, XmlPullParserException, SocketTimeoutException
	{
		System.out.println("GETUSERRESPONSE");
		SoapObject request = new SoapObject(ServerUtilities.NAMESPACE, ServerUtilities.LOGIN);
		request.addProperty("username", username);
		request.addProperty("paswoord", paswoord);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		SoapObject response = null;
		HttpTransportSE ht = new HttpTransportSE(ServerUtilities.URL, 10000);
		ht.call(ServerUtilities.NAMESPACE + ServerUtilities.LOGIN, envelope);
		response = (SoapObject) envelope.getResponse();
		return response;
	}


	/**
	 * Make a {@link User} object from the response from the server.
	 * 
	 * @param response
	 */
	private UserTO getUser(SoapObject response) throws TechnicalException
	{
		System.out.println(response.getPropertySafelyAsString("error"));
		ServerResult result = ServerResult.valueOf(response.getPropertySafelyAsString("error"));
		if (result == ServerResult.SUCCESS)
		{
			SoapObject userto = (SoapObject) response.getProperty("userTO");
			String achternaam = userto.getPropertySafelyAsString("achternaam").toString();
			String email = userto.getPropertySafelyAsString("email").toString();
			int id = Integer.parseInt(userto.getPropertySafelyAsString("userid").toString());
			String naam = userto.getPropertySafelyAsString("naam").toString();
			boolean admin = Boolean.getBoolean(userto.getPropertySafelyAsString("admin").toString());
			String gcmID = userto.getPropertySafelyAsString("GCMid");
			return new UserTO(id, naam, achternaam, email, admin, gcmID);
		}
		else
		{
			throw new TechnicalException(result.toString());
		}
	}
}
