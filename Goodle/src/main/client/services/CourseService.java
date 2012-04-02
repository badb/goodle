package main.client.services;

import java.util.Collection;

import main.client.utils.CourseShortDesc;
import main.shared.models.Course;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath(value = "course")
public interface CourseService extends RemoteService
{
	public Collection<CourseShortDesc> findCoursesDescByName(String name);

}
