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
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;
import be.cegeka.android.alarms.transferobjects.UserTO;

/**
 * The class that makes the connection with the web service using SOAP messages.
 * 
 */
public class RemoteDBSoapRequest extends Observable {
	// Method names.
	public static final String GET_USER = "getUser";
	public static final String GET_ALARMS_FROM_USER = "getAlarmsFromUser";
	public static final String REGISTER_SENDERID = "registerSenderID";
	public static final String UNREGISTER_SENDERID = "unRegisterSenderID";

	// METHOD NAME
	private static String METHOD_NAME = "getAlarmsFromUser";
	// NAMESPACE/METHOD
	private static String SOAP_ACTION = "http://cegeka.be/getAlarmsFromUser";
	// TARGETNAMESPACE IN WSDL
	private static final String NAMESPACE = "http://cegeka.be/";
	// URL OF WSDL FILE
	private static final String URL = "http://172.29.162.254:8080/AlarmWebService/AlarmWebService";

	private UserTO user;
	private ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();

	public void execute(String... uri) {
		new InnerRequestTask().execute(uri);
	}

	private class InnerRequestTask
			extends
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
				if (METHOD_NAME.equals(REGISTER_SENDERID)) {
					String email = uri[1];
					String senderID = uri[2];
					response = soapSendSenderID(email, senderID);
				}
				successfulConnection = true;
			} catch (SocketTimeoutException Exception) {
				Exception.printStackTrace();
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
				} else if (METHOD_NAME.equals(REGISTER_SENDERID)) {
					// TODO HANDLE RESULT
				}
			}
			if (successfulConnection) {
				setChanged();
				notifyObservers();
			}
			super.onPostExecute(result);
		}
		private SoapObject soapSendSenderID(String email, String senderID) throws IOException, XmlPullParserException {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("emailadres", email);
			request.addProperty("senderID", senderID);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			SoapObject response = null;
			HttpTransportSE ht = new HttpTransportSE(URL, 10000);
			ht.call(SOAP_ACTION, envelope);
			response = (SoapObject) envelope.getResponse();
			return response;

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
		private SoapObject getUserResponse(String username, String paswoord) throws IOException, XmlPullParserException, SocketTimeoutException {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("emailadres", username);
			request.addProperty("paswoord", paswoord);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
			UserTO u;
			String achternaam = response.getPropertySafelyAsString("achternaam").toString();
			String email = response.getPropertySafelyAsString("emailadres").toString();
			int id = Integer.parseInt(response.getPropertySafelyAsString("id").toString());
			String naam = response.getPropertySafelyAsString("naam").toString();
			boolean admin = Boolean.getBoolean(response.getPropertySafelyAsString("admin").toString());
			u = new UserTO(id, naam, achternaam, email, admin);
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
				String paswoord) throws IOException, XmlPullParserException, SocketTimeoutException {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("emailadres", username);
			request.addProperty("paswoord", paswoord);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
			ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();
			for (int i = 0; i < response.getPropertyCount(); i++) {
				SoapObject o = (SoapObject) response.getProperty(i);
				String info = o.getProperty("info").toString();
				int id = Integer.parseInt(o.getPropertySafelyAsString("id").toString());
				String titel = o.getPropertySafelyAsString("title").toString();

				int repeatUnit = Integer.parseInt(o.getPropertySafelyAsString("repeatUnit", -1).toString());
				int repeatQuantity = Integer.parseInt(o.getPropertySafelyAsString("repeatQuantity").toString());
				long date = Long.parseLong(o.getPropertySafelyAsString("date").toString());
				long endDate = Long.parseLong(o.getPropertySafelyAsString("repeatEndDate").toString());
				AlarmTO a = null;
				if (repeatUnit == -1) {
					a = new AlarmTO(id, titel, info, date);
					alarms.add(a);
				} else {
					a = new RepeatedAlarmTO(repeatUnit, repeatQuantity, endDate, id, titel, info, date);
					alarms.add(a);
				}
			}
			setAlarms(alarms);
		}

	}

	public UserTO getUser() {
		return user;
	}

	public void setUser(UserTO user) {
		RemoteDBSoapRequest.this.user = user;
	}

	public ArrayList<AlarmTO> getAlarms() {
		alarms.removeAll(Collections.singleton(null));
		return alarms;
	}

	public void setAlarms(ArrayList<AlarmTO> alarms) {
		RemoteDBSoapRequest.this.alarms = alarms;
	}

}