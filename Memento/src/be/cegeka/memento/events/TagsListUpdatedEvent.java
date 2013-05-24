package be.cegeka.memento.events;

import java.util.List;

import be.cegeka.android.ShouldrTap.AbstractEvent;

public class TagsListUpdatedEvent extends AbstractEvent<List<String>> {
	
	private List<String> tags;

	@Override
	public List<String> getData() {
		return tags;
	}

	@Override
	public void setData(List<String> tags) {
		this.tags = tags;
	}

}
