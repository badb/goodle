package main.client.mapper;

import main.client.place.CoursePlace;
import main.client.place.CreateCourseImportPlace;
import main.client.place.CreateCoursePlace;
import main.client.place.FindCoursesByNamePlace;
import main.client.place.UserMainPagePlace;
import main.client.place.UserProfilePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers
({
	CoursePlace.Tokenizer.class, 
	FindCoursesByNamePlace.Tokenizer.class, 
	CreateCoursePlace.Tokenizer.class,
	CreateCourseImportPlace.Tokenizer.class,
	UserMainPagePlace.Tokenizer.class,
	UserProfilePlace.Tokenizer.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {}
