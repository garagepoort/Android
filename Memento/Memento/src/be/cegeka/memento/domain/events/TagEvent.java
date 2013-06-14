package be.cegeka.memento.domain.events;

import be.cegeka.android.ShouldrTap.AbstractEvent;


public class TagEvent extends AbstractEvent<String>
{
	private String result;


	@Override
	public String getData()
	{
		return result;
	}


	@Override
	public void setData(String result)
	{
		this.result = result;
	}
}
