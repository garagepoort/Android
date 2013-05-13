package be.cegeka.alarms.android.client.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import be.cegeka.android.alarms.transferobjects.AlarmTO;


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
//			Bundle bundle = intent.getExtras();
			Intent newIntent = new Intent(context, AlarmReceiverActivity.class);
			newIntent.putExtra("Alarm", new AlarmTO(5, "test titel", "test info", 564654546));
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);
		}
		catch (Exception e)
		{
			Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
			e.printStackTrace();

		}
	}

}
