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

public class UnregisterSenderIDTask extends FutureTask<Boolean, String> {
	
	private Context context;
	public UnregisterSenderIDTask(Context ctx){
		this.context=ctx;
	}

	@Override
	protected Boolean doInBackgroundFuture(String... arguments) throws Exception {
		SoapPrimitive response = soapUnregisterSenderID(arguments[0]);
		return getResponse(response);
	}
	
	private SoapPrimitive soapUnregisterSenderID(String email) throws IOException, XmlPullParserException {
		
		
		SoapObject request = new SoapObject(getProperty(context,"NAMESPACE"), getProperty(context,"UNREGISTER_SENDERID"));
		request.addProperty("registrationID", email);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapPrimitive response = null;
		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "URL"), 10000);
		ht.call(getProperty(context, "NAMESPACE") + getProperty(context, "UNREGISTER_SENDERID"), envelope);
		response = (SoapPrimitive) envelope.getResponse();
		return response;

	}

	private boolean getResponse(Object response) {
		return Boolean.parseBoolean(response.toString());
	}

}
