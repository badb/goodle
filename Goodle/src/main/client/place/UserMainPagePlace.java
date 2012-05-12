package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserMainPagePlace extends Place 
{
	public static class Tokenizer implements PlaceTokenizer<UserMainPagePlace>
	{
		@Override
		public String getToken(UserMainPagePlace place) { return ""; }
		
		@Override 
		public UserMainPagePlace getPlace(String token) {  return new UserMainPagePlace(); }
	}
}