package be.cegeka.memento.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.qrhelpers.Contents;
import be.cegeka.memento.domain.qrhelpers.QRCodeEncoder;
import be.cegeka.memento.util.SystemUiHider;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


public class QRCodeActivity extends Activity
{
	private static final boolean AUTO_HIDE = true;
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
	private static final boolean TOGGLE_ON_CLICK = true;
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
	private SystemUiHider mSystemUiHider;
	private String tag;
	private String contact;
	private Bitmap qrcode;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qrcode);
		setupActionBar();

		tag = getIntent().getExtras().getString("TAG");
		if (tag != null)
		{
			setTitle(getString(R.string.qrcode_title_tag) + " " + tag);
			qrcode = generateQRBitmap(tag);
		}
		else
		{
			contact = getIntent().getExtras().getString("CONTACT");
			if (contact != null)
			{
				setTitle(getString(R.string.qrcode_title_contact));
				qrcode = generateQRBitmap(contact);
			}
		}

		setQRImage();
		setUpAutoUIHide();
	}


	private void setQRImage()
	{
		ImageView qrImageView = (ImageView) findViewById(R.id.qrImage);
		if (qrcode != null)
		{
			qrImageView.setImageBitmap(qrcode);
		}
	}


	private Bitmap generateQRBitmap(String tag)
	{
		Bitmap bitmap = null;
		if (tag == null)
		{
			DialogCreator.showErrorDialog(getString(R.string.dialog_qr_show_no_tag_message), this);
		}
		else
		{
			try
			{
				int qrCodeDimention = 250;
				QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(tag, null,
						Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
				bitmap = qrCodeEncoder.encodeAsBitmap();
			}
			catch (WriterException e)
			{
				e.printStackTrace();
			}
		}
		return bitmap;
	}


	private void setUpAutoUIHide()
	{
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener()
				{
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;


					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible)
					{
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
						{
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0)
							{
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0)
							{
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						}
						else
						{
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
						}

						if (visible && AUTO_HIDE)
						{
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (TOGGLE_ON_CLICK)
				{
					mSystemUiHider.toggle();
				}
				else
				{
					mSystemUiHider.show();
				}
			}
		});
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}


	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
	{
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent)
		{
			if (AUTO_HIDE)
			{
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			mSystemUiHider.hide();
		}
	};


	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis)
	{
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
