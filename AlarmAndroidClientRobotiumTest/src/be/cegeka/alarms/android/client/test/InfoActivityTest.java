package be.cegeka.alarms.android.client.test;

import org.easymock.EasyMock;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.activities.SavedAlarmsActivity;
import be.cegeka.alarms.android.client.infrastructure.InternetChecker;

import com.jayway.android.robotium.solo.Solo;


public class InfoActivityTest extends ActivityInstrumentationTestCase2<InfoActivity> {

	private Solo solo; 
	private InternetChecker internetChecker;
	
	public InfoActivityTest() {
		super(InfoActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		internetChecker = EasyMock.createMock(InternetChecker.class);
//		getActivity().setInternetChecker(internetChecker);
	}

	public void testCheckNoInternetDialog() {
//		EasyMock.expect(internetChecker.isNetworkAvailable(getActivity())).andReturn(false);
		
		solo.clickOnButton("Log In");
		solo.waitForDialogToOpen(2000);
		assertTrue(solo.searchText("No internet connection. Try again later"));
		solo.clickOnButton("Ok");
	}

	public void testCheckLoginActivity() {
//		EasyMock.expect(internetChecker.isNetworkAvailable(getActivity())).andReturn(true);
		
		solo.clickOnButton("Log In");
		solo.waitForActivity(LoginActivity.class, 2000);
		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in or register");
		solo.waitForActivity(Activity.class);
		solo.assertCurrentActivity("", InfoActivity.class);
	}

	public void testShowAlarms() {
		solo.clickOnButton("Show Alarms");
		solo.waitForActivity(SavedAlarmsActivity.class);
		assertTrue(solo.searchText("Saved Alarms"));
	}
}
