package be.cegeka.android.dwaaldetectie.model;

import java.util.ArrayList;
import java.util.Collection;
import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * Own implementation of an ArrayAdapter to be used as the Adapter providing the
 * data to the suggestions list when the user searches for an address. Needed
 * because a standard ArrayAdapter will filter results from the list, while in
 * this case we want all results put into the Adapter to be returned.
 */
public class AddressSuggestionListAdapter extends ArrayAdapter<String>
{
	private ArrayList<String> adapter;


	public AddressSuggestionListAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
		adapter = new ArrayList<String>();
	}


	@Override
	public void add(String object)
	{
		adapter.add(object);
	}


	@Override
	public void addAll(Collection<? extends String> collection)
	{
		adapter.addAll(collection);
	}


	@Override
	public int getCount()
	{
		return adapter.size();
	}


	@Override
	public String getItem(int position)
	{
		return adapter.get(position);
	}


	@Override
	public void clear()
	{
		adapter.clear();
	}
}
