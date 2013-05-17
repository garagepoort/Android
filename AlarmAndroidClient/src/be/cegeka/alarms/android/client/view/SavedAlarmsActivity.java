package be.cegeka.alarms.android.client.view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
import be.cegeka.android.alarms.transferobjects.AlarmTO;

public class SavedAlarmsActivity extends Activity implements Observer {

	private ListView listView;

	@Override
	protected void onStart() {
		super.onStart();
		AlarmsModel.getInstance().addObserver(this);
		showAlarms();
	}

	@Override
	protected void onDestroy() {
		AlarmsModel.getInstance().deleteObserver(this);
		super.onDestroy();
	}

	public void showAlarms() {
		listView = (ListView) findViewById(R.id.AlarmenListView);
		ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(this, android.R.layout.simple_list_item_1, AlarmsModel.getInstance().getAlarms());
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AlarmListItemClickListener());
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof List<?>) {
			List<AlarmTO> alarms = (List<AlarmTO>) data;
			listView = (ListView) findViewById(R.id.AlarmenListView);
			ArrayAdapter<AlarmTO> arrayAdapter = new ArrayAdapter<AlarmTO>(this, android.R.layout.simple_list_item_1, alarms);
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
