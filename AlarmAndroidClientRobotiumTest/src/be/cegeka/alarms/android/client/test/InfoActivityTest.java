package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.activities.SavedAlarmsActivity;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.infrastructure.LoginController;

import com.jayway.android.robotium.solo.Solo;


public class InfoActivityTest extends ActivityInstrumentationTestCase2<InfoActivity> {

	private Solo solo; 
	private InternetChecker internetCheckerMock;
	private LoginController loginController;
	
	public InfoActivityTest() {
		super(InfoActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		getActivity().setInternetChecker(internetCheckerMock);
		
		internetCheckerMock = mock(InternetChecker.class);
		loginController = mock(LoginController.class);
		getActivity().setInternetChecker(internetCheckerMock);
		getActivity().setLoginController(loginController);
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
//		when(loginController.isUserLoggedIn(getActivity())).thenReturn(true);
		assertTrue(solo.searchButton("Sync Now"));
	}

	public void testShowAlarms() {
		solo.clickOnButton("Show Alarms");
		solo.waitForActivity(SavedAlarmsActivity.class);
		assertTrue(solo.searchText("Saved Alarms"));
	}
	
	
	public void tearDown(){
		solo.finishOpenedActivities();
	}
	
}
