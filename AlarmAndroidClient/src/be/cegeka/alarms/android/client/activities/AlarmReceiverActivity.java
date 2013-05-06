package be.cegeka.alarms.android.client.activities;

import java.io.IOException;
import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import be.cegeka.alarms.android.client.R;
import be.cegeka.alarms.android.client.exception.DatabaseException;
import be.cegeka.alarms.android.client.localAlarmSync.AlarmToAndroidSchedulerSyncer;
import be.cegeka.alarms.android.client.localDB.LocalAlarmRepository;
import be.cegeka.android.alarms.transferobjects.AlarmTO;
import be.cegeka.android.alarms.transferobjects.RepeatedAlarmTO;


public class AlarmReceiverActivity extends Activity
{
	private LocalAlarmRepository localAlarmRepository;
	private MediaPlayer mMediaPlayer;
	private AlarmTO alarm;
	boolean loaded = false;
	Dialog mDialog;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		System.out.println("AlarmReceiverActivity erin");
		
		this.localAlarmRepository = new LocalAlarmRepository(this);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		checkAlarm();
		initAndShowViews();
		playSound(this, getAlarmUri());
		deleteIfNotRepeatedAlarm();
	}


	private void checkAlarm()
	{
		Object o = getIntent().getSerializableExtra("Alarm");
		if (o instanceof RepeatedAlarmTO)
		{
			setAlarm((RepeatedAlarmTO) o);
			setNextAlarm();
		}
		else
		{
			setAlarm((AlarmTO) o);
		}
	}


	private void initAndShowViews()
	{
		mDialog = new Dialog(this);
		mDialog.setTitle(getAlarm().getTitle());
		mDialog.setCancelable(false);
		mDialog.setContentView(R.layout.alarm);

		TextView descrfield = (TextView) mDialog.findViewById(R.id.description);
		descrfield.setText(getAlarm().getInfo());

		Button stopAlarm = (Button) mDialog.findViewById(R.id.stopAlarm);
		stopAlarm.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				stopActivityAndMediaplayer();
				return false;
			}
		});
		mDialog.show();
	}


	@Override
	public void onAttachedToWindow()
	{
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}


	private void stopActivityAndMediaplayer()
	{
		mMediaPlayer.stop();
		finish();
		mDialog.cancel();
	}


	@Override
	protected void onPause()
	{
		// if the lock screen is on then we don't need to stop the mediaplayer
		// cause the onPause method will be called.
		// If the screen is locked the sound still has to continue playing.
		KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		boolean showing = kgMgr.inKeyguardRestrictedInputMode();
		if (!showing)
		{
			stopActivityAndMediaplayer();
		}
		super.onPause();
	}


	private void playSound(Context context, Uri alert)
	{
		mMediaPlayer = new MediaPlayer();
		((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
		try
		{
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0)
			{
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		}
		catch (IOException e)
		{
			System.out.println("OOPS");
		}

	}


	private Uri getAlarmUri()
	{
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null)
		{
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null)
			{
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}


	private void deleteIfNotRepeatedAlarm()
	{
		if (!(getAlarm() instanceof RepeatedAlarmTO))
		{
			deleteAlarm(getAlarm());
		}
	}


	private void deleteAlarm(AlarmTO alarm)
	{
		try
		{
			localAlarmRepository.deleteAlarm(alarm);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}


	private void setNextAlarm()
	{
		try
		{
			RepeatedAlarmTO repeatedAlarm = (RepeatedAlarmTO) getAlarm();
			repeatedAlarm = localAlarmRepository.updateRepeatedAlarm(repeatedAlarm);
			AlarmToAndroidSchedulerSyncer.scheduleAlarm(this, repeatedAlarm);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}


	public AlarmTO getAlarm()
	{
		return alarm;
	}


	public void setAlarm(AlarmTO alarm)
	{
		this.alarm = alarm;
	}
}
