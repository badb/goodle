package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CourseModulesEditPlace extends Place 
{
	private String courseId;
	public String getCourseId() { return courseId; }
	
	private String groupId;
	public String getGroupId() { return groupId; }
	
	private String tabId;
	public String getTabId() { return tabId; }

	public void setTabId(String tabId) { this.tabId = tabId; }

	public CourseModulesEditPlace(String courseId, String groupId, String tabId) 
	{ 
		this.courseId = courseId;
		this.groupId = groupId;
		this.tabId = tabId;
	}
	
	public static class Tokenizer implements PlaceTokenizer<CourseModulesEditPlace>
	{
		@Override
		public String getToken(CourseModulesEditPlace place) 
		{ 
			return place.getCourseId() + ":" + place.getGroupId() + ":" + place.getTabId(); 
		}
		
		@Override 
		public CourseModulesEditPlace getPlace(String token) { 
			String [] ids = token.split(":");
			return new CourseModulesEditPlace(ids[0], ids[1], ids[2]); 
		}
	}
}

