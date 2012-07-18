package main.shared.proxy;

import java.util.List;

import main.server.domain.Course;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Course.class)
public interface CourseRequest extends RequestContext 
{
	InstanceRequest<CourseProxy, Void> addCoordinator(Long id);
	InstanceRequest<CourseProxy, Void> removeCoordinator(Long id);
	InstanceRequest<CourseProxy, Void> addMember(Long id);
	InstanceRequest<CourseProxy, Void> removeMember(Long id);
	InstanceRequest<CourseProxy, Void> addModule(Long id);
	InstanceRequest<CourseProxy, Void> removeModule(Long id);
	InstanceRequest<CourseProxy, Void> addMessage(MessageProxy message);
	InstanceRequest<CourseProxy, Void> removeMessage(MessageProxy message);
	
	InstanceRequest<CourseProxy, Boolean> registerCurrentUser(String key);
	InstanceRequest<CourseProxy, Boolean> unregisterUsers(List<Long> ids);
	InstanceRequest<CourseProxy, CourseProxy> update();
	InstanceRequest<CourseProxy, Boolean> addDescription(String desc);
	InstanceRequest<CourseProxy, Boolean> addBiblio(String bibl);
	
	InstanceRequest<CourseProxy, Long> persist();
	InstanceRequest<CourseProxy, Void> remove();
	Request<CourseProxy> findCourse(Long id);
	Request<List<CourseProxy>> findCoursesByName(String name);
	Request<List<CourseProxy>> getAllCourses();
	Request<List<String>> getAllCoursesNames();
	
	Request<CourseProxy> newCourse();

}
