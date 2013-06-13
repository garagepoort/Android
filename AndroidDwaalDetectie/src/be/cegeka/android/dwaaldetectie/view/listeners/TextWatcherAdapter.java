package be.cegeka.android.dwaaldetectie.view.listeners;

import android.text.Editable;
import android.text.TextWatcher;


/**
 * Adapter that implements the TextWatcher interface and implements some of
 * the methods with no content. The onTextChanged method stays abstract.
 */
public abstract class TextWatcherAdapter implements TextWatcher
{
	@Override
	public void afterTextChanged(Editable s)
	{

	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{

	}
}
