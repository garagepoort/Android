package be.cegeka.alarms.android.client.serverconnection;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import be.cegeka.android.flibture.FutureTask;

public class UnregisterSenderIDTask extends FutureTask<Boolean, String> {

	@Override
	protected Boolean doInBackgroundFuture(String... arguments) throws Exception {
		SoapPrimitive response = soapUnregisterSenderID(arguments[0]);
		return getResponse(response);
	}
	
	private SoapPrimitive soapUnregisterSenderID(String email) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(ServerUtilities.NAMESPACE, ServerUtilities.UNREGISTER_SENDERID);
		request.addProperty("email", email);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapPrimitive response = null;
		HttpTransportSE ht = new HttpTransportSE(ServerUtilities.URL, 10000);
		ht.call(ServerUtilities.NAMESPACE + ServerUtilities.UNREGISTER_SENDERID, envelope);
		response = (SoapPrimitive) envelope.getResponse();
		return response;

	}

	private boolean getResponse(Object response) {
		return Boolean.parseBoolean(response.toString());
	}

}
