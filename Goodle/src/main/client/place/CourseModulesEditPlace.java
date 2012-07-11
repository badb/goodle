package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CourseModulesEditPlace extends Place 
{
	private String courseId;
	public String getCourseId() { return courseId; }

	public CourseModulesEditPlace(String courseId) 
	{ 
		this.courseId = courseId;
	}
	
	public static class Tokenizer implements PlaceTokenizer<CourseModulesEditPlace>
	{
		@Override
		public String getToken(CourseModulesEditPlace place) 
		{ 
			return place.getCourseId();
		}
		
		@Override 
		public CourseModulesEditPlace getPlace(String token) {
			return new CourseModulesEditPlace(token); 
		}
	}
}

