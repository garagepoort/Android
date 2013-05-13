package be.cegeka.alarms.android.client.serverconnection.remoteAlarmSync;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import be.cegeka.alarms.android.client.serverconnection.ServerUtilities;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import be.cegeka.android.flibture.FutureTask;

public class GetAlarmsTask extends FutureTask<List<AlarmTO>, String> {

	@Override
	protected ArrayList<AlarmTO> doInBackgroundFuture(String... uri) throws Exception {
		SoapObject response = null;
		String email = uri[0];
		response = soapGetAlarmsFromUserResponse(email);
		return getAlarms(response);
	}

	private SoapObject soapGetAlarmsFromUserResponse(String username) throws IOException, XmlPullParserException, SocketTimeoutException {
		SoapObject request = new SoapObject(ServerUtilities.NAMESPACE, ServerUtilities.GET_ALARMS_FROM_USER);
		request.addProperty("username", username);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		HttpTransportSE ht = new HttpTransportSE(ServerUtilities.URL, 10000);
		ht.call(ServerUtilities.NAMESPACE
				+ ServerUtilities.GET_ALARMS_FROM_USER, envelope);
		response = (SoapObject) envelope.bodyIn;

		return response;
	}

	private ArrayList<AlarmTO> getAlarms(SoapObject response) {
		ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject o = (SoapObject) response.getProperty(i);
			String info = o.getProperty("info").toString();
			int id = Integer.parseInt(o.getPropertySafelyAsString("alarmID").toString());
			String titel = o.getPropertySafelyAsString("title").toString();
			long date = Long.parseLong(o.getPropertySafelyAsString("dateInMillis").toString());

			int repeatUnit = Integer.parseInt(o.getPropertySafelyAsString("repeatUnit", 0).toString());
			int repeatQuantity = Integer.parseInt(o.getPropertySafelyAsString("repeatQuantity", 0).toString());
			long endDate = Long.parseLong(o.getPropertySafelyAsString("repeatEnddate", 0).toString());

			AlarmTO a = null;
			if (repeatUnit == 0) {
				a = new AlarmTO(id, titel, info, date);
				alarms.add(a);
			} else {
				a = new RepeatedAlarmTO(repeatUnit, repeatQuantity, endDate, id, titel, info, date);
				alarms.add(a);
			}
		}
		return alarms;
	}

}
