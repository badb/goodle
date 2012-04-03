package main.client.services;

import java.util.Collection;

import main.client.utils.CourseShortDesc;
import main.shared.models.Course;
import main.shared.models.DataModificationFailedException;
import main.shared.models.GoodleUser;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface CourseServiceAsync 
{
	public void createCourse
	(
			String name,
			String term,
			String desc, 
			Collection<GoodleUser> teachers, 
			Collection<GoodleUser> members, 
			Link calendar,
			AsyncCallback<Void> callback
	) throws DataModificationFailedException;
	
	public void findCoursesDescByName
	(
			String name, 
			AsyncCallback<Collection<CourseShortDesc>> callback
	);
	public void modifyCourse
	(
			Key courseKey,
			String desc, 
			String term,
			AsyncCallback<Void> callback
	) throws DataModificationFailedException;

}
