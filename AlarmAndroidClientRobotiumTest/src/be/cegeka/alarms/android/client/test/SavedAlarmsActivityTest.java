package be.cegeka.alarms.android.client.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import android.test.ActivityInstrumentationTestCase2;
import be.cegeka.alarms.android.client.activities.SavedAlarmsActivity;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;
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


	public void test_givenLocalAlarmsRepositoryReturnsListOfAlarmTOs_thenTheyAreDisplayed()
	{
		List<AlarmTO> alarmsList = new ArrayList<AlarmTO>();
		AlarmTO alarmTO_1 = new AlarmTO(66, "title_1", "info_1", 4658752);
		AlarmTO alarmTO_2 = new AlarmTO(47, "title_2", "info_2", 2357896);
		alarmsList.add(alarmTO_1);
		alarmsList.add(alarmTO_2);

		when(localAlarmRepository.getLocalAlarms(getActivity())).thenReturn(alarmsList);
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				SavedAlarmsActivityTest.this.getActivity().showAlarms();
			}
		});
		assertTrue(solo.searchText("title_1"));
	}
}
