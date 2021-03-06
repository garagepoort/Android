package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.domain.infrastructure.InternetChecker;
import be.cegeka.alarms.android.client.domain.login.LoginController;
import be.cegeka.alarms.android.client.view.InfoActivity;
import be.cegeka.alarms.android.client.view.LoginActivity;
import be.cegeka.alarms.android.client.view.SavedAlarmsActivity;

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
	}

	public void testCheckLoginActivity() {
		when(internetCheckerMock.isNetworkAvailable(getActivity())).thenReturn(true);

		solo.clickOnButton("Log In");
		solo.waitForActivity(LoginActivity.class, 2000);
		solo.assertCurrentActivity("", LoginActivity.class);
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
	
	// On teardown always finish all the opened activities.
	public void tearDown(){
		solo.finishOpenedActivities();
	}
	
}
