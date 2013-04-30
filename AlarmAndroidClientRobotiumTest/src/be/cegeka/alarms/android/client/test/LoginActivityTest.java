package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import synchronisation.RemoteAlarmController;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.sync.remoteSync.RemoteDBConnectionInterface;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.*;
import com.jayway.android.robotium.solo.Solo;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>
{
	public LoginActivityTest()
	{
		super(LoginActivity.class);
	}
//
//	private Solo solo;
//	private RemoteAlarmController remoteAlarmController;
//	private UserTO userTO;
//
//
//	protected void setUp() throws Exception
//	{
//		solo = new Solo(getInstrumentation(), getActivity());
//		remoteAlarmController = mock(RemoteAlarmController.class);
//		//getActivity().set(remoteAlarmController);
//		userTO = new UserTO(2, "naam", "achternaam", "paswoord", "repeatPaswoord", "email", false);
//	}
//
//
//	protected void tearDown()
//	{
//		LoginController loginController = new LoginController(getActivity());
//		if (loginController.isUserLoggedIn())
//		{
//			loginController.logOutUser();
//		}
//		solo.finishOpenedActivities();
//	}
//
//
//	public void test_whenCancelIsPressed_thenLoginActivityIsClosedAndInfoActivityIsCurrent()
//	{
//		solo.clickOnButton("Cancel");
//		solo.assertCurrentActivity(null, InfoActivity.class);
//	}
//
//
//	public void test_givenRemoteDBConnectionReturnsUserTO_thenMessageLoginSuccessfullAndUserDetailsShow()
//	{
//		when(remoteAlarmController.login(anyString(), anyString())).thenAnswer(new Answer<UserTO>()
//		{
//			@Override
//			public UserTO answer(InvocationOnMock invocation) throws Throwable
//			{
//				// Uncomment to test behaviour with long responsetime
//				// Thread.sleep(3000);
//				return userTO;
//			}
//		});
//
//		solo.enterText(0, "david.s.maes@gmail.com");
//		solo.enterText(1, "password");
//		solo.clickOnButton("Sign in");
//
//		solo.assertCurrentActivity(null, InfoActivity.class);
//		assertTrue(solo.searchText("Login succesfull"));
//		assertTrue(solo.searchText("naam achternaam"));
//	}
//
//
//	public void test_givenRemoteDBConnectionReturnsNull_thenMessageWrongPasswordShows()
//	{
//		when(remoteAlarmController.login(anyString(), anyString())).thenAnswer(new Answer<UserTO>()
//		{
//			@Override
//			public UserTO answer(InvocationOnMock invocation) throws Throwable
//			{
//				// Uncomment to test behaviour with long responsetime
//				// Thread.sleep(3000);
//				return null;
//			}
//		});
//
//		solo.enterText(0, "david.s.maes@gmail.com");
//		solo.enterText(1, "password");
//		solo.clickOnButton("Sign in");
//
//		assertTrue(solo.searchText("This password is incorrect"));
//		solo.assertCurrentActivity(null, LoginActivity.class);
//	}
//
//
//	public void test_givenLoggedIn_whenLogOutPressed_thenInfoActivityIsUpdated()
//	{
//		when(remoteAlarmController.login(anyString(), anyString())).thenReturn(userTO);
//
//		solo.enterText(0, "david.s.maes@gmail.com");
//		solo.enterText(1, "password");
//		solo.clickOnButton("Sign in");
//
//		solo.assertCurrentActivity(null, InfoActivity.class);
//		assertTrue(solo.searchText("Login succesfull"));
//		assertTrue(solo.searchText("naam achternaam"));
//		
//		solo.clickOnButton("Log Out");
//		solo.assertCurrentActivity(null, InfoActivity.class);
//		assertTrue(solo.searchText("Log In"));
//		assertTrue(solo.searchText("You are currently not logged in."));
//	}
}
