package be.cegeka.memento.model;

import java.util.List;

import be.cegeka.android.ShouldrTap.Tapper;

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
}
