package com.cegeka.alarmmanager.view;

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

import com.cegeka.alarmmanager.R;
import com.cegeka.alarmmanager.db.LocalAlarmRepository;
import com.cegeka.alarmmanager.model.Alarm;
import com.cegeka.alarmmanager.model.RepeatedAlarm;
import com.cegeka.alarmmanager.sync.localSync.AlarmToAndroidSchedulerSyncer;

public class AlarmReceiverActivity extends Activity {

	private MediaPlayer mMediaPlayer;
	private Alarm alarm;
	boolean loaded = false;
	Dialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		checkAlarm();
		initAndShowViews();
		playSound(this, getAlarmUri());
		deleteIfNotRepeatedAlarm();
	}

	private void checkAlarm() {
		Object o = getIntent().getSerializableExtra("Alarm");
		if (o instanceof RepeatedAlarm) {
			setAlarm((RepeatedAlarm) o);
			setNextAlarm();
		} else {
			setAlarm((Alarm) o);
		}
	}

	private void initAndShowViews() {
		mDialog = new Dialog(this);
		mDialog.setTitle(getAlarm().getTitle());
		mDialog.setCancelable(false);
		mDialog.setContentView(R.layout.alarm);

		TextView descrfield = (TextView) mDialog.findViewById(R.id.description);
		descrfield.setText(getAlarm().getDescription());

		Button stopAlarm = (Button) mDialog.findViewById(R.id.stopAlarm);
		stopAlarm.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				stopActivityAndMediaplayer();
				return false;
			}
		});
		mDialog.show();
	}

	@Override
	public void onAttachedToWindow() {
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	private void stopActivityAndMediaplayer() {
		mMediaPlayer.stop();
		finish();
		mDialog.cancel();
	}

	@Override
	protected void onPause() {
		// if the lock screen is on then we don't need to stop the mediaplayer
		// cause the onPause method will be called.
		// If the screen is locked the sound still has to continue playing.
		KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		boolean showing = kgMgr.inKeyguardRestrictedInputMode();
		if (!showing) {
			stopActivityAndMediaplayer();
		}
		super.onPause();
	}

	private void playSound(Context context, Uri alert) {
		mMediaPlayer = new MediaPlayer();
		((Activity) context).setVolumeControlStream(AudioManager.STREAM_MUSIC);
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		} catch (IOException e) {
			System.out.println("OOPS");
		}

	}

	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}

	private void deleteIfNotRepeatedAlarm() {
		if (!(getAlarm() instanceof RepeatedAlarm)) {
			deleteAlarm(getAlarm());
		}
	}

	private void deleteAlarm(Alarm alarm) {
		LocalAlarmRepository.deleteAlarm(getApplicationContext(), alarm);
	}

	private void setNextAlarm() {
		RepeatedAlarm repeatedAlarm = (RepeatedAlarm) getAlarm();
		repeatedAlarm = LocalAlarmRepository.updateRepeatedAlarm(this,
				repeatedAlarm);
		AlarmToAndroidSchedulerSyncer.scheduleAlarm(this, repeatedAlarm);
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
}
