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
import com.google.gson.Gson;


public class SubscribeToTagTask extends FutureTask<Integer, String>
{
	private Context context;


	public SubscribeToTagTask(Context context)
	{
		this.context = context;
	}


	@Override
	protected Integer doInBackgroundFuture(String... arguments) throws Exception
	{
		SoapObject response = null;
		String gcmID = arguments[0];
		String tag = arguments[1];
		response = subscribe(gcmID, tag);
		return handleResponse(response);
	}


	private SoapObject subscribe(String gcmID, String tag) throws IOException, XmlPullParserException, SocketTimeoutException
	{
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "SUBSCRIBE_TO_TAG"));
		request.addProperty("gcmID", gcmID);
		request.addProperty("tag", tag);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;
		HttpTransportSE ht = new HttpTransportSE(getSharedPreference(context, "URL", "http://1.1.1.1:8080/MementoServer/MementoWebService"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "SUBSCRIBE_TO_TAG"), envelope);
		response = (SoapObject) envelope.bodyIn;
		System.out.println(response);
		return response;
	}


	private Integer handleResponse(SoapObject response) throws TechnicalException
	{
		SoapObject soapObject = (SoapObject) response.getProperty("return");
		String message = soapObject.getPropertySafelyAsString("exceptionMessage");
		if (message != null && !message.equals(""))
		{
			throw new TechnicalException(message);
		}
		else
		{
			String data = soapObject.getPropertyAsString("data");
			Gson gson = new Gson();
			return gson.fromJson(data, Integer.class);
		}
	}
}
