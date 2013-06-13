package be.cegeka.android.dwaaldetectie.utilities;

import android.location.Address;


public class AddressConverter
{
	/**
	 * Formats an Address object into a human readable String.
	 * 
	 * @param address
	 *            The Address object to describe.
	 * @return String describing the Address.
	 */
	public static String convertAddress(Address address)
	{
		String message = address.getAddressLine(0);

		if (address.getAddressLine(1) != null)
		{
			message += ", " + address.getAddressLine(1);
			if (address.getAddressLine(2) != null)
			{
				message += ", " + address.getAddressLine(2);
			}
		}

		return message;
	}
}
