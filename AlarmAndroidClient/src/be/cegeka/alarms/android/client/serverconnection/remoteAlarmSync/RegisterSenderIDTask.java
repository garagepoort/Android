package be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import be.cegeka.alarms.android.client.futureimplementation.FutureTask;
import be.cegeka.alarms.android.client.futureimplementation.exception.FutureException;
import be.cegeka.alarms.android.client.serverconnection.ServerUtilities;


public class RegisterSenderIDTask extends FutureTask<Boolean, String>
{

	private boolean timedOut;

	@Override
	protected Boolean doInBackgroundFuture(String... uri) throws FutureException
	{
		SoapPrimitive response = null;
		try
		{
			String email = uri[0];
			String senderID = uri[1];
			response = soapSendSenderID(email, senderID);
		}
		catch (IOException e)
		{
			timedOut = true;
			throw new FutureException("Timed out");
		}
		catch (XmlPullParserException e)
		{
			throw new FutureException("Something went wrong");
		}
		return getResponse(response);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecuteFuture(Boolean result)
	{

	}


	private SoapPrimitive soapSendSenderID(String email, String senderID) throws IOException, XmlPullParserException
	{
		SoapObject request = new SoapObject(ServerUtilities.NAMESPACE, ServerUtilities.REGISTER_SENDERID);
		request.addProperty("email", email);
		request.addProperty("gcmId", senderID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapPrimitive response = null;
		HttpTransportSE ht = new HttpTransportSE(ServerUtilities.URL, 10000);
		ht.call(ServerUtilities.NAMESPACE + ServerUtilities.REGISTER_SENDERID, envelope);
		response = (SoapPrimitive) envelope.getResponse();
		return response;

	}


	private boolean getResponse(Object response)
	{
		return Boolean.parseBoolean(response.toString());
	}

}
