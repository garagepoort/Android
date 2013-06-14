package be.cegeka.memento.receiver;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

	public class GCMReceiver extends GCMBroadcastReceiver { 
	    @Override
		protected String getGCMIntentServiceClassName(Context context) { 
			return "be.cegeka.memento.domain.gcm.GCMIntentService"; 
		} 
	}

