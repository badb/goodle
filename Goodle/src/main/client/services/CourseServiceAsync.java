package main.client.services;

import java.util.Collection;

import main.client.utils.CourseShortDesc;
import main.shared.models.Course;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface CourseServiceAsync 
{
	public void findCoursesDescByName
	(
			String name, 
			AsyncCallback<Collection<CourseShortDesc>> callback
	);

}
