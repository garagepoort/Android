package be.cegeka.alarms.android.client.test;

<<<<<<< HEAD
import org.easymock.EasyMock;
=======
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
>>>>>>> cbb3f0f58334054c42ee6787580c5f95a615591e
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.activities.SavedAlarmsActivity;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;
<<<<<<< HEAD
=======
import be.cegeka.alarms.android.client.infrastructure.LoginController;
>>>>>>> cbb3f0f58334054c42ee6787580c5f95a615591e

import com.jayway.android.robotium.solo.Solo;


public class InfoActivityTest extends ActivityInstrumentationTestCase2<InfoActivity> {

	private Solo solo; 
<<<<<<< HEAD
	private InternetChecker internetChecker;
=======
	private InternetChecker internetCheckerMock;
	private LoginController loginController;
>>>>>>> cbb3f0f58334054c42ee6787580c5f95a615591e
	
	public InfoActivityTest() {
		super(InfoActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
<<<<<<< HEAD
		internetChecker = EasyMock.createMock(InternetChecker.class);
//		getActivity().setInternetChecker(internetChecker);
=======
		
		internetCheckerMock = mock(InternetChecker.class);
		loginController = mock(LoginController.class);
		getActivity().setInternetChecker(internetCheckerMock);
		getActivity().setLoginController(loginController);
>>>>>>> cbb3f0f58334054c42ee6787580c5f95a615591e
	}

	public void testCheckNoInternetDialog() {
		when(internetCheckerMock.isNetworkAvailable(getActivity())).thenReturn(false);

		solo.clickOnButton("Log In");
		solo.waitForDialogToOpen(2000);
		assertTrue(solo.searchText("No internet connection. Try again later"));
		solo.clickOnButton("Ok");
	}

	public void testCheckLoginActivity() {
		when(internetCheckerMock.isNetworkAvailable(getActivity())).thenReturn(true);

		solo.clickOnButton("Log In");
		solo.waitForActivity(LoginActivity.class, 2000);
		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in or register");
		solo.waitForActivity(Activity.class);
		solo.assertCurrentActivity("", InfoActivity.class);
	}
	
	public void testGivenLoggedIn_ThenShowSyncButton(){
		when(loginController.isUserLoggedIn(getActivity())).thenReturn(true);
		assertTrue(solo.searchButton("Sync Now"));
	}

	public void testShowAlarms() {
		solo.clickOnButton("Show Alarms");
		solo.waitForActivity(SavedAlarmsActivity.class);
		assertTrue(solo.searchText("Saved Alarms"));
	}
}
