package main.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserProfilePlace extends Place 
{
	private String userId;
	
	public UserProfilePlace(String token) { userId = token; } 
	
	public String getUserId() { return userId; }

	public static class Tokenizer implements PlaceTokenizer<UserProfilePlace>
	{
		@Override
		public String getToken(UserProfilePlace place) { return place.getUserId(); }
		
		@Override 
		public UserProfilePlace getPlace(String token) 
		{ 
			return new UserProfilePlace(token); 
		}
	}
}