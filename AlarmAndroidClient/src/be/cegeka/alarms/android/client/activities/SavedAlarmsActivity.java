package be.cegeka.alarms.android.client.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.alarms.android.client.tempProbleemMetJarHierGewoneSrcFiles.AlarmTO;


public class SavedAlarmsActivity extends Activity
{

	private ListView listView;
	private LocalAlarmRepository localAlarmRepository;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_alarms);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_alarms, menu);
		return true;
	}


	@Override
	protected void onStart()
	{
		super.onStart();
		
		localAlarmRepository = new LocalAlarmRepository();
	}
	
	
	public void showAlarms()
	{
		List<AlarmTO> localAlarms = localAlarmRepository.getLocalAlarms(this);
		listView = (ListView) findViewById(R.id.AlarmenListView);
		ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(this, android.R.layout.simple_list_item_1, localAlarms);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AlarmListItemClickListener());
	}


	private class AlarmListItemClickListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub

		}
	}


	/**
	 * ONLY FOR TESTING.
	 * 
	 * @param localAlarmRepository
	 */
	public void setLocalAlarmRepository(LocalAlarmRepository localAlarmRepository)
	{
		this.localAlarmRepository = localAlarmRepository;
	}
}
