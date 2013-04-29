package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.InfoActivity;
import be.cegeka.alarms.android.client.activities.LoginActivity;
import be.cegeka.alarms.android.client.infrastructure.LoginController;
import be.cegeka.alarms.android.client.sync.remoteSync.RemoteDBConnectionInterface;
import be.cegeka.alarms.android.client.sync.remoteSync.RemoteDBWebConnection;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.*;
import com.jayway.android.robotium.solo.Solo;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>
{
	public LoginActivityTest()
	{
		super(LoginActivity.class);
	}

	private Solo solo;
	private RemoteDBConnectionInterface remoteDBConnectionInterface;
	private UserTO userTO;


	protected void setUp() throws Exception
	{
		solo = new Solo(getInstrumentation(), getActivity());
		remoteDBConnectionInterface = mock(RemoteDBWebConnection.class);
		getActivity().setRemoteDBConnectionInterface(remoteDBConnectionInterface);
		userTO = new UserTO(2, "naam", "achternaam", "paswoord", "repeatPaswoord", "email", false);
	}


	protected void tearDown()
	{
		LoginController loginController = new LoginController(getActivity());
		if(loginController.isUserLoggedIn())
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
		when(remoteDBConnectionInterface.login(anyString(), anyString())).thenAnswer(new Answer<UserTO>()
		{
			@Override
			public UserTO answer(InvocationOnMock invocation) throws Throwable
			{
				// Uncomment to test behaviour with long responsetime
				// Thread.sleep(3000);
				return userTO;
			}
		});

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");

		solo.assertCurrentActivity(null, InfoActivity.class);
		assertTrue(solo.searchText("Login succesfull"));
		assertTrue(solo.searchText("naam achternaam"));
	}


	public void test_givenRemoteDBConnectionReturnsNull_thenMessageWrongPasswordShows()
	{
		when(remoteDBConnectionInterface.login(anyString(), anyString())).thenAnswer(new Answer<UserTO>()
		{
			@Override
			public UserTO answer(InvocationOnMock invocation) throws Throwable
			{
				// Uncomment to test behaviour with long responsetime
				// Thread.sleep(3000);
				return null;
			}
		});

		solo.enterText(0, "david.s.maes@gmail.com");
		solo.enterText(1, "password");
		solo.clickOnButton("Sign in");

		assertTrue(solo.searchText("This password is incorrect"));
		solo.assertCurrentActivity(null, LoginActivity.class);
	}
	
	
}
