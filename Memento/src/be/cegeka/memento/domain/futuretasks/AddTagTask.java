package be.cegeka.memento.domain.futuretasks;

import static be.cegeka.memento.domain.utilities.PropertyReader.getProperty;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import be.cegeka.android.flibture.FutureTask;
import be.cegeka.memento.domain.exception.TechnicalException;
import be.cegeka.memento.domain.utilities.PropertyReader;

public class AddTagTask extends FutureTask<Void, String> {
	private Context context;
	private SoapObject response;

	public AddTagTask(Context context) {
		this.context = context;
	}
	@Override
	protected Void doInBackgroundFuture(String... uri) throws Exception {
		try {
			String gcmID = uri[0];
			String tag = uri[1];
			response = soapGetAddTagResponse(gcmID, tag);
		} catch (Exception exception) {
			throw new TechnicalException(PropertyReader.getProperty(context, "exceptions.properties", "NO_SERVER_CONNECTION"));
		}

		handleResponse(response);
		return null;
	}

	private SoapObject soapGetAddTagResponse(String gcmID, String tag) throws IOException, XmlPullParserException, SocketTimeoutException {
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "ADD_TAG"));
		request.addProperty("tag", tag);
		request.addProperty("gcmID", gcmID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "config.properties", "URL"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "ADD_TAG"), envelope);
		response = (SoapObject) envelope.bodyIn;

		return response;
	}

	private void handleResponse(SoapObject response) throws Exception {
		SoapObject so = (SoapObject) response.getProperty("return");
		String message = so.getPropertySafelyAsString("exceptionMessage");
		if (message != null && !message.equals("")) {
			throw new Exception(message);
		}
	}
}
