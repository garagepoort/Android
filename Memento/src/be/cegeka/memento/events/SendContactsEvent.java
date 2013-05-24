package be.cegeka.memento.events;

import be.cegeka.android.ShouldrTap.AbstractEvent;


public class SendContactsEvent extends AbstractEvent<Integer>
{
	Integer ontvangers;


	@Override
	public Integer getData()
	{
		return ontvangers;
	}


	@Override
	public void setData(Integer ontvangers)
	{
		this.ontvangers = ontvangers;
	}

}
