package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoursePlace extends Place 
{
	private String courseId;
	public String getCourseId() { return courseId; }
	
	private String viewName;
	public String getViewName() { return viewName; }

	public void setViewName(String viewName) { this.viewName = viewName; }

	public CoursePlace(String courseId, String viewName) 
	{ 
		this.courseId = courseId;
		this.viewName = viewName;
	}
	
	public static class Tokenizer implements PlaceTokenizer<CoursePlace>
	{
		@Override
		public String getToken(CoursePlace place) 
		{ 
			return place.getCourseId() + ":" + place.getViewName(); 
		}
		
		@Override 
		public CoursePlace getPlace(String token) { 
			String [] ids = token.split(":");
			return new CoursePlace(ids[0], ids[1]); 
		}
	}
}
