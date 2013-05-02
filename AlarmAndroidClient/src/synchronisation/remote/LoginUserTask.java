package synchronisation.remote;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import be.cegeka.android.alarms.transferobjects.UserTO;
import futureimplementation.Future;
import futureimplementation.FutureCallable;
import futureimplementation.FutureService;
import futureimplementation.ResultCode;


public class LoginUserTask extends AsyncTask<String, String, SoapObject>
{

	private Future<UserTO> future;
	private boolean timedOut;


	public LoginUserTask(Future<UserTO> future)
	{
		this.future = future;
	}


	@Override
	protected SoapObject doInBackground(String... uri)
	{
		SoapObject response = null;
		try
		{
			String email = uri[0];
			String paswoord = uri[1];
			response = getUserResponse(email, paswoord);
		}
		catch (IOException e)
		{
			timedOut = true;
			e.printStackTrace();
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		return response;
	}


	@Override
	protected void onPostExecute(SoapObject result)
	{
		
		if (timedOut || result == null)
		{
			future.setValue(null, ResultCode.SERVER_RELATED_ERROR);
		}
		else if (getUser(result) == null)
		{
			future.setValue(null, ResultCode.WRONG_USER_CREDENTIALS);
		}
		else if (getUser(result) != null)
		{
			future.setValue(getUser(result), ResultCode.SUCCESS);
		}
		super.onPostExecute(result);
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
	private UserTO getUser(SoapObject response)
	{
		String achternaam = response.getPropertySafelyAsString("achternaam").toString();
		String email = response.getPropertySafelyAsString("email").toString();
		int id = Integer.parseInt(response.getPropertySafelyAsString("userid").toString());
		String naam = response.getPropertySafelyAsString("naam").toString();
		boolean admin = Boolean.getBoolean(response.getPropertySafelyAsString("admin").toString());
		System.out.println(email);
		return new UserTO(id, naam, achternaam, email, admin);

	}
}
