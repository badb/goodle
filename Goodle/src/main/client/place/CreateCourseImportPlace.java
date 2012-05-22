package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CreateCourseImportPlace extends Place 
{
	public static class Tokenizer implements PlaceTokenizer<CreateCourseImportPlace>
	{
		@Override
		public String getToken(CreateCourseImportPlace place) { return "i"; }
		
		@Override 
		public CreateCourseImportPlace getPlace(String token) { return new CreateCourseImportPlace(); }
	}

}
