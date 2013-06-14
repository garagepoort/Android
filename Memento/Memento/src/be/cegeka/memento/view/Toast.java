package be.cegeka.memento.view;

import android.content.Context;


public class Toast
{
	public static android.widget.Toast showBlueToast(Context context, String message)
	{
		android.widget.Toast toast = android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG);
		toast.getView().setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
		toast.show();
		return toast;
	}
}
