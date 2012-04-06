package main.client.services;

import java.util.Collection;

import main.client.utils.CourseShortDesc;
import main.shared.models.Course;
import main.shared.models.DataModificationFailedException;
import main.shared.models.GoodleUser;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(value = "course")
public interface CourseService extends RemoteService
{
	public void createCourse
	(
			String name,
			String term,
			String desc, 
			Link calendar
	) throws DataModificationFailedException;
	
	public Collection<CourseShortDesc> findCoursesDescByName(String name);
	
	public void modifyCourse
	(
			Key courseKey,
			String desc, 
			String term
	) throws DataModificationFailedException;

}
