package be.cegeka.alarms.android.client.gcm;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

	public class GCMReceiver extends GCMBroadcastReceiver { 
	    @Override
		protected String getGCMIntentServiceClassName(Context context) { 
			return "be.cegeka.alarms.android.client.gcm.GCMIntentService"; 
		} 
	}

