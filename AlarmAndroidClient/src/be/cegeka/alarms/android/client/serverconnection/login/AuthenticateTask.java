package be.cegeka.alarms.android.client.serverconnection.login;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import be.cegeka.alarms.android.client.futureimplementation.FutureTask;
import be.cegeka.alarms.android.client.futureimplementation.ResultCode;
import be.cegeka.alarms.android.client.serverconnection.ServerUtilities;


public class AuthenticateTask extends FutureTask
{

	private boolean timedOut;


	public AuthenticateTask()
	{
	}


	@Override
	protected SoapPrimitive doInBackground(String... uri)
	{
		SoapPrimitive response = null;
		try
		{
			String email = uri[0];
			String password = uri[1];
			response = authenticate(email, password);
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


	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result)
	{

		if (timedOut)
		{
			getFuture().setValue(null, ResultCode.SERVER_RELATED_ERROR);
		}
		else if (result != null && getResponse(result))
		{
			getFuture().setValue(getResponse(result), ResultCode.SUCCESS);
		}
		else
		{
			getFuture().setValue(null, ResultCode.WRONG_USER_CREDENTIALS);
		}

		super.onPostExecute(result);
	}


	private SoapPrimitive authenticate(String email, String password) throws IOException, XmlPullParserException
	{
		SoapObject request = new SoapObject(ServerUtilities.NAMESPACE, ServerUtilities.AUTHENTICATE);
		request.addProperty("username", email);
		request.addProperty("password", password);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapPrimitive response = null;
		HttpTransportSE ht = new HttpTransportSE(ServerUtilities.URL, 10000);
		ht.call(ServerUtilities.NAMESPACE + ServerUtilities.AUTHENTICATE, envelope);
		response = (SoapPrimitive) envelope.getResponse();
		return response;

	}


	private boolean getResponse(Object response)
	{
		return Boolean.parseBoolean(response.toString());
	}

}
