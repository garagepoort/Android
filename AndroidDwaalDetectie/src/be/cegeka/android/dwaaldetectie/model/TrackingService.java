package be.cegeka.android.dwaaldetectie.model;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import be.cegeka.android.dwaaldetectie.view.listeners.LocationChangeListener;


public class TrackingService extends Service
{
	private LocationManager lm;
	private Timer timer;
	private static boolean running;
	private LocationListener locationChangeListener;


	public TrackingService()
	{
		timer = new Timer();
		running = false;
	}


	/**
	 * Returns if the Tracking Service is currently running or not.
	 * 
	 * @return True if the Service is running, false if the Service is not
	 *         running.
	 */
	public static boolean isRunning()
	{
		return running;
	}


	/**
	 * This method is called when the TrackingService is started using an
	 * Intent. It will start the service and periodically check for the current
	 * location of the user.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		TrackingConfiguration.trackingConfig().loadVariables(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (locationChangeListener == null)
		{
			locationChangeListener = new LocationChangeListener(this);
		}

		if (!running)
		{
			running = true;

			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationChangeListener);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationChangeListener);

			timer.scheduleAtFixedRate(new TimerTask()
			{
				@Override
				public void run()
				{
					new Handler(Looper.getMainLooper()).post(new Runnable()
					{
						@Override
						public void run()
						{
							lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationChangeListener);
							lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationChangeListener);
						}
					});

					new Timer().schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							lm.removeUpdates(locationChangeListener);
						}
					}, 10000);
				}
			}, 1000, 40000);
		}

		return START_STICKY;
	}


	/**
	 * This method is called when the TrackingService is stopped using an
	 * Intent.
	 */
	public void onDestroy()
	{
		TrackingConfiguration.trackingConfig().setDistanceInfo("");
		lm.removeUpdates(locationChangeListener);
		timer.cancel();
		running = false;

		super.onDestroy();
	}


	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
