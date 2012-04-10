package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateCoursePlace extends Place 
{
	public static class Tokenizer implements PlaceTokenizer<CreateCoursePlace>
	{
		@Override
		public String getToken(CreateCoursePlace place) { return ""; }
		
		@Override 
		public CreateCoursePlace getPlace(String token) { return new CreateCoursePlace(); }
	}

}
