package models;

public class Event {
	
	private String linkName;
	
	public Event(String linkName){
		this.linkName = linkName;
	}

	@Override
	public String toString() {
		
		return linkName;
	}

}
