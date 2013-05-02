package synchronisation.remote;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import futureimplementation.Future;
import futureimplementation.ResultCode;


public class AuthenticateTask extends AsyncTask<String, String, SoapPrimitive>
{

	private Future<Boolean> future;
	private boolean timedOut;


	public AuthenticateTask(Future<Boolean> future)
	{
		this.future = future;
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
	protected void onPostExecute(SoapPrimitive result)
	{

		if (timedOut)
		{
			future.setValue(null, ResultCode.SERVER_RELATED_ERROR);
		}
		else if (result != null && getResponse(result))
		{
			future.setValue(getResponse(result), ResultCode.SUCCESS);
		}
		else
		{
			future.setValue(null, ResultCode.WRONG_USER_CREDENTIALS);
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


	private boolean getResponse(SoapPrimitive response)
	{
		return Boolean.parseBoolean(response.toString());
	}
}
