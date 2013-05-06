package be.cegeka.alarms.android.client.futureimplementation;

public enum ResultCode
{
	SUCCESS("SUCCES"),
	SERVER_RELATED_ERROR("There was a problem logging in, please try again later"),
	WRONG_USER_CREDENTIALS("These user credentials are not in our database"),
	GCM_REGISTRATION_FAILED("Unable to subscribe to the GCM server, alarms will not be automatically updated");

	private final String text;


	private ResultCode(String text)
	{
		this.text = text;
	}


	@Override
	public String toString()
	{
		return this.text;
	}
}
