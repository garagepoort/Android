package be.cegeka.memento.domain.futuretasks;

import static be.cegeka.memento.domain.utilities.PropertyReader.getProperty;
import static be.cegeka.memento.domain.utilities.SharedPrefsManager.getSharedPreference;
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


public class UnsubscribeFromTagTask extends FutureTask<Void, String>
{

	private Context context;


	public UnsubscribeFromTagTask(Context context)
	{
		this.context = context;
	}


	@Override
	protected Void doInBackgroundFuture(String... arguments) throws Exception
	{
		SoapObject response = null;
		String gcmID = arguments[0];
		String tag = arguments[1];
		response = subscribe(gcmID, tag);
		handleResponse(response);
		return null;
	}


	private void handleResponse(SoapObject response) throws TechnicalException
	{
		SoapObject so = (SoapObject) response.getProperty("return");
		String message = so.getPropertySafelyAsString("exceptionMessage");
		if (message != null && !message.equals(""))
		{
			throw new TechnicalException(message);
		}
	}


	private SoapObject subscribe(String gcmID, String tag) throws IOException, XmlPullParserException, SocketTimeoutException
	{
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "UNSUBSCRIBE_FROM_TAG"));
		request.addProperty("gcmID", gcmID);
		request.addProperty("tag", tag);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		HttpTransportSE ht = new HttpTransportSE(getSharedPreference(context, "URL", "http://1.1.1.1:8080/MementoServer/MementoWebService"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "UNSUBSCRIBE_FROM_TAG"), envelope);
		response = (SoapObject) envelope.bodyIn;
		System.out.println(response);
		return response;
	}

}
