package be.cegeka.memento.domain.utilities;


public class Group {
	private String tag;
	private int noMembers;
	
	public Group(String tag, int noMembers) {
		super();
		this.tag = tag;
		this.noMembers = noMembers;
	}

	public String getTag() {
		return tag;
	}

	public int getNoMembers() {
		return noMembers;
	}

	@Override
	public String toString() {
		return tag + "   (" + noMembers+")";
	}

	
}
