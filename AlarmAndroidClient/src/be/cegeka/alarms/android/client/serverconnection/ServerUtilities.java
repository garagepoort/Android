package be.cegeka.alarms.android.client.serverconnection;

public class ServerUtilities
{
	// Method names.
	public static final String LOGIN = "login";
	public static final String GET_ALARMS_FROM_USER = "getAllAlarmsFromUser";
	public static final String REGISTER_SENDERID = "registerUser";
	public static final String UNREGISTER_SENDERID = "unRegisterSenderID";
	public static final String AUTHENTICATE = "authenticate";

	// TARGETNAMESPACE IN WSDL
	public static final String NAMESPACE = "http://webservice/";
	// URL OF WSDL FILE
	public static final String URL = "http://172.29.163.229:8080/AlarmManagerWeb/AlarmManagerWebservice";
}
