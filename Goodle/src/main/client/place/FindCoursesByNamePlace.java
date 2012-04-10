package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class FindCoursesByNamePlace extends Place 
{
	private String name;
	
	public FindCoursesByNamePlace(String token) { name = token; }
	
	public String getName() { return name; }
	
	public static class Tokenizer implements PlaceTokenizer<FindCoursesByNamePlace>
	{
		@Override
		public String getToken(FindCoursesByNamePlace place) { return place.getName(); }
		
		@Override 
		public FindCoursesByNamePlace getPlace(String token) { return new FindCoursesByNamePlace(token); }
	}

}
