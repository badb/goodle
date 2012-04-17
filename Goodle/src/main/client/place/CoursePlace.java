package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoursePlace extends Place 
{
	private String courseId;
	private String tabId;
	
	public CoursePlace(String token1, String token2) { 
		courseId = token1;
		tabId = token2;
	}
	
	public String getCourseId() { return courseId; }
	
	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public static class Tokenizer implements PlaceTokenizer<CoursePlace>
	{
		@Override
		public String getToken(CoursePlace place) { return place.getCourseId()+":" +place.getTabId(); }
		
		@Override 
		public CoursePlace getPlace(String token) { 
			String [] ids = token.split(":");
			if (ids.length < 2) {
				return new CoursePlace(ids[0], "1");
			}
			return new CoursePlace(ids[0], ids[1]); 
		}
	}
}
