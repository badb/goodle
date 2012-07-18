package main.client.mapper;

import main.client.place.CoursePlace;
import main.client.place.FindCoursesByNamePlace;
import main.client.place.UserMainPagePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers
({
	CoursePlace.Tokenizer.class, 
	FindCoursesByNamePlace.Tokenizer.class, 
	UserMainPagePlace.Tokenizer.class,
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
