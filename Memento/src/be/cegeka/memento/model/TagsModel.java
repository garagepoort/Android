package be.cegeka.memento.model;

import java.util.Collections;
import java.util.List;

import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.memento.events.TagsListUpdatedEvent;

public class TagsModel extends Tapper {
	private final static TagsModel INSTANCE = new TagsModel();
	private List<String> tags;

	public static TagsModel getInstance(){
		return INSTANCE;
	}
	
	private TagsModel(){
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
		
		TagsListUpdatedEvent event = new TagsListUpdatedEvent();
		event.setData(tags);
		tapShoulders(event);
	}

	public List<String> getTags()
	{
		return Collections.unmodifiableList(tags);
	}
}
