package be.cegeka.alarms.android.client.test;

//<<<<<<< HEAD
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import synchronisation.RemoteAlarmController;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.sync.remoteSync.RemoteDBConnectionInterface;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.*;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.UserTO;

import com.jayway.android.robotium.solo.Solo;

import futureimplementation.Future;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>
{
	public LoginActivityTest()
	{
		super(LoginActivity.class);
	}
//<<<<<<< HEAD
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
//=======

	private Solo solo;
	private RemoteAlarmController remoteAlarmControllerMock;
	private UserTO userTO;


	protected void setUp() throws Exception
	{
		solo = new Solo(getInstrumentation(), getActivity());
		remoteAlarmControllerMock = mock(RemoteAlarmController.class);
		getActivity().setRemoteAlarmController(remoteAlarmControllerMock);
		userTO = new UserTO(2, "naam", "achternaam", "paswoord", "repeatPaswoord", "email", false);
	}


	protected void tearDown()
	{
		LoginController loginController = new LoginController(getActivity());
		if (loginController.isUserLoggedIn())
		{
			loginController.logOutUser();
		}
		solo.finishOpenedActivities();
	}


	public void test_whenCancelIsPressed_thenLoginActivityIsClosedAndInfoActivityIsCurrent()
	{
		solo.clickOnButton("Cancel");
		solo.assertCurrentActivity(null, InfoActivity.class);
	}


	public void test_givenRemoteDBConnectionReturnsUserTO_thenMessageLoginSuccessfullAndUserDetailsShow()
	{
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);
		
		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");
		f.setValue(userTO);
		solo.assertCurrentActivity(null, InfoActivity.class);
		assertTrue(solo.searchText("Login succesfull"));
		assertTrue(solo.searchText("naam achternaam"));
	}


	public void test_givenRemoteDBConnectionReturnsNull_thenMessageWrongPasswordShows()
	{
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");
		f.setValue(null);
		assertTrue(solo.searchText("This password is incorrect"));
		solo.assertCurrentActivity(null, LoginActivity.class);
	}


	public void test_givenLoggedIn_whenLogOutPressed_thenInfoActivityIsUpdated()
	{
		Future<UserTO> f = new Future<UserTO>();
		when(remoteAlarmControllerMock.loginUser(anyString(), anyString())).thenReturn(f);

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");
		f.setValue(userTO);
		solo.assertCurrentActivity(null, InfoActivity.class);
		assertTrue(solo.searchText("Login succesfull"));
		assertTrue(solo.searchText("naam achternaam"));
		
		solo.clickOnButton("Log Out");
		solo.assertCurrentActivity(null, InfoActivity.class);
		assertTrue(solo.searchText("Log In"));
		assertTrue(solo.searchText("You are currently not logged in."));
	}
//>>>>>>> 0090d49e13d544d8cd64bb660b8172c30f96a0a3
}
