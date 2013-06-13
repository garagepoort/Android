package com.cegeka.alarmmanager.services;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cegeka.alarmmanager.infrastructure.InternetChecker;
import com.cegeka.alarmmanager.sync.AlarmSyncer;
import com.cegeka.alarmmanager.utilities.UserLoginLogOut;

public class SyncService extends Service
{

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				if (UserLoginLogOut.userLoggedIn(SyncService.this))
				{
					if (InternetChecker.isNetworkAvailable(SyncService.this))
					{
						AlarmSyncer syncAndRescheduler = AlarmSyncer
								.getInstance();
						syncAndRescheduler.syncAllAlarms(SyncService.this);
						System.out.println("SCHEDULED");
					}
				} else
				{
					timer.cancel();
				}
			}
		}, 1, 10000);
		
		return START_STICKY;
	}

}
