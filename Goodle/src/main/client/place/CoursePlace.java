package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoursePlace extends Place 
{
	private String courseId;
	public String getCourseId() { return courseId; }
	
	private String groupId;
	public String getGroupId() { return groupId; }
	
	private String tabId;
	public String getTabId() { return tabId; }

	public void setTabId(String tabId) { this.tabId = tabId; }

	public CoursePlace(String courseId, String groupId, String tabId) 
	{ 
		this.courseId = courseId;
		this.groupId = groupId;
		this.tabId = tabId;
	}
	
	public static class Tokenizer implements PlaceTokenizer<CoursePlace>
	{
		@Override
		public String getToken(CoursePlace place) 
		{ 
			return place.getCourseId() + ":" + place.getGroupId() + ":" + place.getTabId(); 
		}
		
		@Override 
		public CoursePlace getPlace(String token) { 
			String [] ids = token.split(":");
			return new CoursePlace(ids[0], ids[1], ids[2]); 
		}
	}
}
