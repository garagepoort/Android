package be.cegeka.alarms.android.client.domain.alarmSync;

import static be.cegeka.android.flibture.Future.whenResolved;
import java.util.List;
import android.content.Context;
import android.widget.Toast;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.domain.alarmSync.localAlarmSync.LocalToAndroidAlarmSyncer;
import be.cegeka.alarms.android.client.domain.controllers.LocalAlarmRepository;
import be.cegeka.alarms.android.client.domain.controllers.ServerCalls;
import be.cegeka.alarms.android.client.domain.login.LoginController;
import be.cegeka.alarms.android.client.domain.models.AlarmsModel;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.flibture.Future;
import be.cegeka.android.flibture.FutureCallable;


public class AlarmSyncer
{

	public void syncAllAlarms(final Context context)
	{
		System.out.println("SYNC ALARMS");
		Future<List<AlarmTO>> future = new ServerCalls(context).getAllAlarms(new LoginController(context).getLoggedInUser());
		whenResolved(future, new FutureCallable<List<AlarmTO>>()
		{

			@Override
			public void onSucces(List<AlarmTO> result)
			{
				LocalToAndroidAlarmSyncer localToAndroidAlarmSyncer = new LocalToAndroidAlarmSyncer(context);
				LocalAlarmRepository localAlarmRepository = new LocalAlarmRepository(context);

				localToAndroidAlarmSyncer.unscheduleAllAlarms();
				localAlarmRepository.replaceAll(result);
				AlarmsModel.getInstance().setAlarms(result);
				localToAndroidAlarmSyncer.scheduleAllAlarms();
			}


			@Override
			public void onError(Exception e)
			{
				e.printStackTrace();
				Toast.makeText(context, context.getString(R.string.alarm_sync_error), Toast.LENGTH_LONG).show();
			}

		});
	}
}
