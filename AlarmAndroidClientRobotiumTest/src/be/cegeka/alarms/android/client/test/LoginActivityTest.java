package be.cegeka.alarms.android.client.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import synchronisation.RemoteAlarmController;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.gcm.GCMRegister;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.android.alarms.transferobjects.UserTO;
import com.jayway.android.robotium.solo.Solo;
import futureimplementation.Future;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	private Solo solo;
	private RemoteAlarmController remoteAlarmControllerMock;
	private UserTO userTO;
	private GCMRegister gcmRegisterMock;

	public LoginActivityTest() {
		super(LoginActivity.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		remoteAlarmControllerMock = mock(RemoteAlarmController.class);
		gcmRegisterMock = mock(GCMRegister.class);
		Mockito.doNothing().when(gcmRegisterMock).registerWithGCMServer(getActivity());
		getActivity().setRemoteAlarmController(remoteAlarmControllerMock);
		getActivity().setGcmRegister(gcmRegisterMock);
		userTO = new UserTO(2, "naam", "achternaam", "paswoord", "repeatPaswoord", "email", false);
	}

	protected void tearDown() {
		LoginController loginController = new LoginController(getActivity());
		if (loginController.isUserLoggedIn()) {
			loginController.logOutUser();
		}
		solo.finishOpenedActivities();
	}

	public void test_whenCancelIsPressed_thenLoginActivityIsClosedAndInfoActivityIsCurrent() {
		solo.clickOnButton("Cancel");
		solo.assertCurrentActivity(null, InfoActivity.class);
	}

	public void test_givenRemoteDBConnectionReturnsUserTO_thenMessageLoginSuccessfullAndUserDetailsShow() {
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "pass");
		solo.clickOnButton("Sign in");
		f.setValue(userTO);
		solo.assertCurrentActivity(null, InfoActivity.class);
//		assertTrue(solo.searchText("Login succesfull"));
		assertTrue(solo.searchText("naam achternaam"));
	}

	public void test_givenRemoteDBConnectionReturnsNull_thenMessageWrongPasswordShows() {
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");
		f.setValue(null);
		assertTrue(solo.searchText("This password is incorrect"));
		solo.assertCurrentActivity(null, LoginActivity.class);
	}

	public void test_givenLoggedIn_whenLogOutPressed_thenInfoActivityIsUpdated() {
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "pass");
		solo.clickOnButton("Sign in");
		f.setValue(userTO);
		solo.waitForActivity(InfoActivity.class);
		solo.assertCurrentActivity(null, InfoActivity.class);
		// assertTrue(solo.searchText("Login succesfull"));
		assertTrue(solo.searchText("naam achternaam"));

		solo.clickOnButton("Log Out");
		solo.assertCurrentActivity(null, InfoActivity.class);
		assertTrue(solo.searchText("Log In"));
		assertTrue(solo.searchText("You are currently not logged in."));
	}

}
