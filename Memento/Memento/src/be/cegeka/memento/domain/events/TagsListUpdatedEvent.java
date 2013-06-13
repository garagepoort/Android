package be.cegeka.memento.domain.events;

import java.util.List;

import be.cegeka.android.ShouldrTap.AbstractEvent;
import be.cegeka.memento.domain.utilities.Group;

public class TagsListUpdatedEvent extends AbstractEvent<List<Group>> {
	
	private List<Group> tags;

	@Override
	public List<Group> getData() {
		return tags;
	}

	@Override
	public void setData(List<Group> tags) {
		this.tags = tags;
	}

}
