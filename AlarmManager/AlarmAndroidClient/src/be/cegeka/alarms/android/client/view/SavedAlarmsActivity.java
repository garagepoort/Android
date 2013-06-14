package be.cegeka.alarms.android.client.view;

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
import be.cegeka.alarms.android.client.domain.models.AlarmsModel;
import be.cegeka.alarms.android.client.presenter.MainPresenter;
import be.cegeka.alarms.android.client.shouldertap.events.AlarmEvent;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class SavedAlarmsActivity extends Activity {

	private ListView listView;
	private AlarmShoulder alarmShoulder;
	private MainPresenter mainPresenter;
	
	
	@Override
	protected void onStart() {
		super.onStart();
		mainPresenter = new MainPresenter(this);
		alarmShoulder = new AlarmShoulder();
		AlarmsModel.getInstance().addShoulder(alarmShoulder);
		showAlarms();
	}

	@Override
	protected void onDestroy() {
		AlarmsModel.getInstance().removeShoulder(alarmShoulder);
		super.onDestroy();
	}

	public void showAlarms() {
		listView = (ListView) findViewById(R.id.AlarmenListView);
		ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(this, R.layout.my_list_tem, AlarmsModel.getInstance().getAlarms());
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AlarmListItemClickListener());
	}
	
	public void syncAlarms(View view) {
		mainPresenter.updateAlarms();
	}
	
	private class AlarmShoulder extends Shoulder<AlarmEvent>{

		public AlarmShoulder() {
			super(AlarmEvent.class);
		}

		@Override
		public void update(AlarmEvent alarmEvent) {
			List<AlarmTO> alarms = alarmEvent.getData();
			listView = (ListView) findViewById(R.id.AlarmenListView);
			ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(SavedAlarmsActivity.this, R.layout.my_list_tem, alarms);
			listView.setAdapter(arrayAdapter);
			listView.setOnItemClickListener(new AlarmListItemClickListener());
		}
	}

	private class AlarmListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			final AlarmTO alarm = (AlarmTO) listView.getItemAtPosition(position);
			DialogCreator.buildAndShowDialog(getString(R.string.dialog_title_alarm), alarm.toDetailedString(), SavedAlarmsActivity.this);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_alarms);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.saved_alarms, menu);
		return true;
	}
	
}
