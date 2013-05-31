package be.cegeka.memento.domain.futuretasks;

import static be.cegeka.memento.domain.utilities.PropertyReader.getProperty;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.content.Context;
import be.cegeka.android.flibture.FutureTask;
import be.cegeka.memento.domain.exception.TechnicalException;
import be.cegeka.memento.domain.utilities.Group;
import com.google.gson.Gson;


public class GetTagFromUserTask extends FutureTask<ArrayList<Group>, String>
{

	private Context context;


	public GetTagFromUserTask(Context context)
	{
		this.context = context;
	}


	@Override
	protected ArrayList<Group> doInBackgroundFuture(String... arguments) throws Exception
	{
		SoapObject response = null;
		ArrayList<Group> strings = null;
		String gcmID = arguments[0];
		response = soapGetAlarmsFromUserResponse(gcmID);
		strings = handleResponse(response);
		return strings;
	}


	private SoapObject soapGetAlarmsFromUserResponse(String gcmID) throws IOException, XmlPullParserException, SocketTimeoutException
	{
		SoapObject request = new SoapObject(getProperty(context, "config.properties", "NAMESPACE"), getProperty(context, "config.properties", "GET_TAGS_FROM_USER"));
		request.addProperty("gcmID", gcmID);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		HttpTransportSE ht = new HttpTransportSE(getProperty(context, "config.properties", "URL"), 10000);
		ht.call(getProperty(context, "config.properties", "NAMESPACE") + getProperty(context, "config.properties", "GET_TAGS_FROM_USER"), envelope);
		response = (SoapObject) envelope.bodyIn;
		System.out.println(response);
		return response;
	}


	private ArrayList<Group> handleResponse(SoapObject response) throws TechnicalException
	{
		SoapObject soapObject = (SoapObject) response.getProperty("return");
		String data = soapObject.getPropertyAsString("data");
		String message = soapObject.getPropertySafelyAsString("exceptionMessage");
		if (message != null && !message.equals(""))
		{
			throw new TechnicalException(message);
		}
		else
		{
			@SuppressWarnings("unchecked")
			HashMap<String, Double> tags = new Gson().fromJson(data, HashMap.class);
			ArrayList<Group> groups = new ArrayList<Group>();
			for (Entry<String, Double> e : tags.entrySet())
			{
				groups.add(new Group(e.getKey(), e.getValue().intValue()));
			}
			return groups;
		}

	}

}
