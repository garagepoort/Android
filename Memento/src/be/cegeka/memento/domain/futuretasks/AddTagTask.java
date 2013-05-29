package be.cegeka.memento.domain.futuretasks;

import static be.cegeka.memento.domain.infrastructure.PropertyReader.getProperty;

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

public class AddTagTask extends FutureTask<Void, String> {
	private Context context;

	public AddTagTask(Context context) {
		this.context = context;
	}
	@Override
	protected Void doInBackgroundFuture(String... uri) throws Exception {
		try {
			String gcmID = uri[0];
			String tag = uri[1];
			SoapObject response = soapGetAddTagResponse(gcmID, tag);
			handleResponse(response);
			return null;
		} catch (Exception exception) {
			throw new TechnicalException(exception);
		}
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
	//
	// private ArrayList<AlarmTO> handleResponse(SoapObject response) {
	// ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
	// for (int i = 0; i < response.getPropertyCount(); i++) {
	// SoapObject o = (SoapObject) response.getProperty(i);
	// String info = o.getProperty("info").toString();
	// int id =
	// Integer.parseInt(o.getPropertySafelyAsString("alarmID").toString());
	// String titel = o.getPropertySafelyAsString("title").toString();
	// long date =
	// Long.parseLong(o.getPropertySafelyAsString("dateInMillis").toString());
	//
	// int repeatUnit =
	// Integer.parseInt(o.getPropertySafelyAsString("repeatUnit",
	// 0).toString());
	// int repeatQuantity =
	// Integer.parseInt(o.getPropertySafelyAsString("repeatQuantity",
	// 0).toString());
	// long endDate =
	// Long.parseLong(o.getPropertySafelyAsString("repeatEnddate",
	// 0).toString());
	//
	// AlarmTO a = null;
	// if (repeatUnit == 0) {
	// a = new AlarmTO(id, titel, info, date);
	// alarms.add(a);
	// } else {
	// a = new RepeatedAlarmTO(repeatUnit, repeatQuantity, endDate, id, titel,
	// info, date);
	// alarms.add(a);
	// }
	// }
	// return alarms;
	// }
}
