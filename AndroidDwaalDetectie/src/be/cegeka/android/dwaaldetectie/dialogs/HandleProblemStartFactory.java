package be.cegeka.android.dwaaldetectie.dialogs;

import be.cegeka.android.dwaaldetectie.model.TrackingConfiguration;


/**
 * Creates an instance of class implementing the HandleProblemStartInterface
 * according to an error code.
 */
public class HandleProblemStartFactory
{
	/**
	 * Returns the appropriate instance that can handle with the error code.
	 * 
	 * @param result
	 *            Error code, has to be one of the error codes in
	 *            TrackingConfiguration.
	 * @return An instance of a class implementing the
	 *         HandleProblemStartInterface.
	 */
	public HandleProblemStartInterface getHandleProblemStart(int result)
	{
		HandleProblemStartInterface handleProblemStartInterface = null;

		if (result == TrackingConfiguration.RESULT_NO_ADDRESS_SET)
		{
			handleProblemStartInterface = new HandleProblemStartNoAddress();
		}
		else if (result == TrackingConfiguration.RESULT_NO_MAX_DISTANCE)
		{
			handleProblemStartInterface = new HandleProblemStartNoMaxDistance();
		}
		else if (result == TrackingConfiguration.RESULT_ERROR)
		{
			handleProblemStartInterface = new HandleProblemStartError();
		}

		return handleProblemStartInterface;
	}
}
