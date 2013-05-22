package be.cegeka.alarms.android.client.domain.alarmSync.remoteAlarmSync;

import static be.cegeka.alarms.android.client.domain.infrastructure.PropertyReader.getProperty;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import be.cegeka.android.flibture.FutureTask;

public class RegisterSenderIDTask extends FutureTask<Boolean, String> {

	private Context context;

	public RegisterSenderIDTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackgroundFuture(String... uri) throws Exception {
		SoapPrimitive response = null;
		String email = uri[0];
		String senderID = uri[1];
		response = soapSendSenderID(email, senderID);
		return getResponse(response);
	}

	private SoapPrimitive soapSendSenderID(String email, String senderID) throws IOException, XmlPullParserException {
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "REGISTER_SENDERID"));
		request.addProperty("email", email);
		request.addProperty("gcmId", senderID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapPrimitive response = null;
		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "config.properties", "URL"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "REGISTER_SENDERID"), envelope);
		response = (SoapPrimitive) envelope.getResponse();
		return response;

	}

	private boolean getResponse(Object response) {
		return Boolean.parseBoolean(response.toString());
	}

}
