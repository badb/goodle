package main.client.services;

import java.util.Collection;

import main.shared.models.Course;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface CourseServiceAsync 
{
	public void findCoursesByName(String name, AsyncCallback<Collection<Course>> callback);

}
