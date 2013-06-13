package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.domain.controllers.LocalAlarmRepository;
import be.cegeka.alarms.android.client.view.SavedAlarmsActivity;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import com.jayway.android.robotium.solo.Solo;


public class SavedAlarmsActivityTest extends ActivityInstrumentationTestCase2<SavedAlarmsActivity>
{
	private Solo solo;
	private LocalAlarmRepository localAlarmRepository;


	public SavedAlarmsActivityTest()
	{
		super(SavedAlarmsActivity.class);
	}


	protected void setUp() throws Exception
	{
		solo = new Solo(getInstrumentation(), getActivity());
		localAlarmRepository = mock(LocalAlarmRepository.class);
		getActivity().setLocalAlarmRepository(localAlarmRepository);
	}
	
	@Override
	protected void tearDown() throws Exception {
		getActivity().finish();
		solo.finishOpenedActivities();
		//super.tearDown();
	}


	public void test_agivenLocalAlarmsRepositoryReturnsListOfAlarmTOs_thenTheyAreDisplayed()
	{
		//remove call to showAlarms in the onstart method of SavedAlarmsActivity to make this test work.
		List<AlarmTO> alarmsList = new ArrayList<AlarmTO>();
		AlarmTO alarmTO_1 = new AlarmTO(66, "title_1", "info_1", 4658752);
		AlarmTO alarmTO_2 = new AlarmTO(47, "title_2", "info_2", 2357896);
		alarmsList.add(alarmTO_1);
		alarmsList.add(alarmTO_2);
		
		when(localAlarmRepository.getLocalAlarms()).thenReturn(alarmsList);
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				SavedAlarmsActivityTest.this.getActivity().showAlarms();
			}
		});
		assertTrue(solo.searchText("title_1"));
		assertTrue(solo.searchText("title_2"));
		assertTrue(solo.searchText("info_1"));
		assertTrue(solo.searchText("info_2"));
	}
	
	public void test_givenLocalAlarmsRepositoryReturnsEmptyList_thenMessageNoAlarmsShown(){
		
		when(localAlarmRepository.getLocalAlarms()).thenReturn(new ArrayList<AlarmTO>());
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				SavedAlarmsActivityTest.this.getActivity().showAlarms();
			}
		});
		assertTrue(solo.searchText("Currently, you have no alarms."));
	}
}
