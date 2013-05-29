package be.cegeka.memento.model;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.android.ShouldrTap.Tapper;
import be.cegeka.memento.domain.events.TagsListUpdatedEvent;
import be.cegeka.memento.domain.utilities.Group;

public class TagsModel extends Tapper {
	private final static TagsModel INSTANCE = new TagsModel();
	private List<Group> groups;
	private List<String> tags;

	public static TagsModel getInstance(){
		return INSTANCE;
	}
	
	private TagsModel(){
	}

	public void setTags(List<Group> groups) {
		this.groups = groups;
		tags = new ArrayList<String>();
		for(Group g : groups){
			tags.add(g.getTag());
		}
		TagsListUpdatedEvent event = new TagsListUpdatedEvent();
		event.setData(groups);
		tapShoulders(event);
	}

	public List<Group> getGroups()
	{
		return groups;
	}
	
	public List<String> getTags(){
		return tags;
	}
}
