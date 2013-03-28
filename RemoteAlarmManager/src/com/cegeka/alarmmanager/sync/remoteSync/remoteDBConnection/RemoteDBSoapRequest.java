package com.cegeka.alarmmanager.sync.remoteSync.remoteDBConnection;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

import com.cegeka.alarmmanager.model.Alarm;
import com.cegeka.alarmmanager.model.User;
import com.cegeka.alarmmanager.sync.remoteSync.connection.transferobject.AlarmTO;
import com.cegeka.alarmmanager.utilities.AlarmConverter;

/**
 * The class that makes the connection with the web service using SOAP messages.
 * 
 */
public class RemoteDBSoapRequest extends Observable {
	// Method names.
	public static final String GET_USER = "getUser";
	public static final String GET_ALARMS_FROM_USER = "getAlarmsFromUser";

	// METHOD NAME
	private static String METHOD_NAME = "getAlarmsFromUser";
	// NAMESPACE/METHOD
	private static String SOAP_ACTION = "http://cegeka.be/getAlarmsFromUser";
	// TARGETNAMESPACE IN WSDL
	private static final String NAMESPACE = "http://cegeka.be/";
	// URL OF WSDL FILE
	private static final String URL = "http://172.31.207.79:8080/AlarmWebService/AlarmWebService";

	private User user;
	private ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	public void execute(String... uri) {
		new InnerRequestTask().execute(uri);
	}

	private class InnerRequestTask extends
			AsyncTask<String, String, SoapObject> {

		private boolean successfulConnection;

		@Override
		protected SoapObject doInBackground(String... uri) {
			METHOD_NAME = uri[0];
			SOAP_ACTION = NAMESPACE + METHOD_NAME;
			successfulConnection = false;

			SoapObject response = null;
			try {
				if (METHOD_NAME.equals(GET_USER)) {
					String naam = uri[1];
					String paswoord = uri[2];
					response = getUserResponse(naam, paswoord);
				}
				if (METHOD_NAME.equals(GET_ALARMS_FROM_USER)) {
					String naam = uri[1];
					String paswoord = uri[2];
					response = soapGetAlarmsFromUserResponse(naam, paswoord);
				}
				successfulConnection = true;
			} catch (SocketTimeoutException Exception) {
			} catch (IOException e) {
				System.out.println("TIMEOUT EXCEPTION");
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(SoapObject result) {
			if (result != null) {
				if (METHOD_NAME.equals(GET_USER)) {
					setUser(result);
				} else if (METHOD_NAME.equals(GET_ALARMS_FROM_USER)) {
					getAlarms(result);
				}
			}
			if (successfulConnection) {
				setChanged();
				notifyObservers();
			}
			super.onPostExecute(result);
		}

		/**
		 * Connects to the web service asking the web service for a user with
		 * this login credentials.
		 * 
		 * @param username
		 *            The username.
		 * @param paswoord
		 *            The password of the {@link User}.
		 * @return A soapobject if the login credentials were correct or null of
		 *         they were incorrect.
		 * @throws IOException
		 * @throws XmlPullParserException
		 */
		private SoapObject getUserResponse(String username, String paswoord)
				throws IOException, XmlPullParserException,
				SocketTimeoutException {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("emailadres", username);
			request.addProperty("paswoord", paswoord);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			SoapObject response = null;
			HttpTransportSE ht = new HttpTransportSE(URL, 10000);
			ht.call(SOAP_ACTION, envelope);
			response = (SoapObject) envelope.getResponse();
			return response;
		}

		/**
		 * Make a {@link User} object from the response from the server.
		 * 
		 * @param response
		 */
		private void setUser(SoapObject response) {
			User u;
			String achternaam = response
					.getPropertySafelyAsString("achternaam").toString();
			String email = response.getPropertySafelyAsString("emailadres")
					.toString();
			int id = Integer.parseInt(response.getPropertySafelyAsString("id")
					.toString());
			String naam = response.getPropertySafelyAsString("naam").toString();
			u = new User(id, naam, achternaam, email);
			user = u;
		}

		/**
		 * Connects to the web service asking the web service for a the alarms
		 * of the user with these login credentials.
		 * 
		 * @param username
		 *            The username.
		 * @param paswoord
		 *            The password of the {@link User}.
		 * @return A {@link SoapObject} if the user exists else null.
		 * @throws IOException
		 * @throws XmlPullParserException
		 */
		private SoapObject soapGetAlarmsFromUserResponse(String username,
				String paswoord) throws IOException, XmlPullParserException,
				SocketTimeoutException {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("emailadres", username);
			request.addProperty("paswoord", paswoord);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			SoapObject response = null;
			try {
				HttpTransportSE ht = new HttpTransportSE(URL, 10000);
				ht.call(SOAP_ACTION, envelope);
				response = (SoapObject) envelope.bodyIn;
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			}
			return response;
		}

		/**
		 * Make the {@link Alarm} objects from the response.
		 * 
		 * @param response
		 *            A {@link SoapObject} containing the response.
		 */
		private void getAlarms(SoapObject response) {
			ArrayList<Alarm> alarms = new ArrayList<Alarm>();
			for (int i = 0; i < response.getPropertyCount(); i++) {
				SoapObject o = (SoapObject) response.getProperty(i);
				String info = o.getProperty("info").toString();
				int id = Integer.parseInt(o.getPropertySafelyAsString("id")
						.toString());
				AlarmTO a = new AlarmTO();
				a.setId(id);
				a.setTitle(o.getPropertySafelyAsString("title").toString());
				a.setInfo(info);
				a.setRepeated(Boolean.parseBoolean(o.getPropertySafelyAsString(
						"repeated").toString()));
				a.setRepeatUnit(o.getPropertySafelyAsString("repeatUnit")
						.toString());
				a.setRepeatQuantity(Integer
						.parseInt(o.getPropertySafelyAsString("repeatQuantity")
								.toString()));
				a.setDate(Long.parseLong(o.getPropertySafelyAsString("date")
						.toString()));
				a.setRepeatEndDate(Long.parseLong(o.getPropertySafelyAsString(
						"repeatEndDate").toString()));
				Alarm convertedAlarm = AlarmConverter.convertAlarm(a);
				alarms.add(convertedAlarm);
			}
			setAlarms(alarms);
		}

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		RemoteDBSoapRequest.this.user = user;
	}

	public ArrayList<Alarm> getAlarms() {
		alarms.removeAll(Collections.singleton(null));
		return alarms;
	}

	public void setAlarms(ArrayList<Alarm> alarms) {
		RemoteDBSoapRequest.this.alarms = alarms;
	}

}