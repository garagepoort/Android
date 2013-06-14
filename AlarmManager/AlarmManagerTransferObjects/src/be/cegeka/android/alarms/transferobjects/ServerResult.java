/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cegeka.android.alarms.transferobjects;

/**
 *
 * @author hannesc
 */
public enum ServerResult
{
        SUCCESS("Success"),
	WRONG_USER_CREDENTIALS("These user credentials are not in our database"),
	EXCEPTION("Something went wrong.");

	private final String text;


	private ServerResult(String text)
	{
		this.text = text;
	}


	@Override
	public String toString()
	{
		return this.text;
	}
}


