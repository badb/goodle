package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoursePlace extends Place 
{
	private String courseId;
	
	public CoursePlace(String token) { courseId = token; }
	
	public String getCourseId() { return courseId; }
	
	public static class Tokenizer implements PlaceTokenizer<CoursePlace>
	{
		@Override
		public String getToken(CoursePlace place) { return place.getCourseId(); }
		
		@Override 
		public CoursePlace getPlace(String token) { return new CoursePlace(token); }
	}
}
