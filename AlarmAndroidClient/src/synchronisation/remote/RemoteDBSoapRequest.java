package synchronisation.remote;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.RepeatedAlarmTO;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;
import futureimplementation.Future;

/**
 * The class that makes the connection with the web service using SOAP messages.
 * 
 */
public class RemoteDBSoapRequest extends Observable {
	// Method names.
	public static final String LOGIN = "login";
	public static final String GET_ALARMS_FROM_USER = "getAllAlarmsFromUser";
	public static final String REGISTER_SENDERID = "registerSenderID";
	public static final String UNREGISTER_SENDERID = "unRegisterSenderID";

	// METHOD NAME
	private static String METHOD_NAME = "getAlarmsFromUser";
	// NAMESPACE/METHOD
	private static String SOAP_ACTION = "http://webservice/login";
	// TARGETNAMESPACE IN WSDL
	private static final String NAMESPACE = "http://webservice/";
	// URL OF WSDL FILE
	private static final String URL = "http://172.29.162.135:8080/AlarmManagerWeb/AlarmManagerWebservice";

	// private UserTO user;
	// private ArrayList<AlarmTO> alarms = new ArrayList<AlarmTO>();

	private Future<ArrayList<AlarmTO>> futureAlarms;
	private Future<UserTO> futureUser;

	public RemoteDBSoapRequest(Future<ArrayList<AlarmTO>> future, Future<UserTO> futureUser) {
		futureAlarms = future;
		this.futureUser=futureUser;
	}
	

	// public RemoteDBSoapRequest(Future<UserTO> future){
	// futureUser = future;
	// }

	public void execute(String... uri) {
		new InnerRequestTask().execute(uri);
	}

	private class InnerRequestTask
			extends
				AsyncTask<String, String, SoapObject> {


		@Override
		protected SoapObject doInBackground(String... uri) {
			METHOD_NAME = uri[0];
			SOAP_ACTION = getSoapAction();

			SoapObject response = null;
			try {
				if (METHOD_NAME.equals(LOGIN)) {
					System.out.println("LOGIN!!!!!");
					String naam = uri[1];
					String paswoord = uri[2];
					response = getUserResponse(naam, paswoord);
				}
				if (METHOD_NAME.equals(GET_ALARMS_FROM_USER)) {
					String naam = uri[1];
					response = soapGetAlarmsFromUserResponse(naam, paswoord);
				}
				if (METHOD_NAME.equals(REGISTER_SENDERID)) {
					String email = uri[1];
					String senderID = uri[2];
					response = soapSendSenderID(email, senderID);
				}
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
				if (METHOD_NAME.equals(LOGIN)) {
					futureUser.setValue(getUser(result));
				} else if (METHOD_NAME.equals(GET_ALARMS_FROM_USER)) {
					futureAlarms.setValue(getAlarms(result));
				} else if (METHOD_NAME.equals(REGISTER_SENDERID)) {
					// TODO HANDLE RESULT
				}
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
			System.out.println("GETUSERRESPONSE");
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("username", username);
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
		private UserTO getUser(SoapObject response) {
			System.out.println("GETUSERTO");
			String achternaam = response.getPropertySafelyAsString("achternaam").toString();
			String email = response.getPropertySafelyAsString("emailadres").toString();
			int id = Integer.parseInt(response.getPropertySafelyAsString("userid").toString());
			String naam = response.getPropertySafelyAsString("naam").toString();
			boolean admin = Boolean.getBoolean(response.getPropertySafelyAsString("admin").toString());
			System.out.println(achternaam);
			return new UserTO(id, naam, achternaam, email, admin);

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
		private ArrayList<AlarmTO> getAlarms(SoapObject response) {
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
			return alarms;
		}
		
		
		private String getSoapAction(){
			return "\"" + NAMESPACE + METHOD_NAME + "\"";
		}

	}

	
	
}