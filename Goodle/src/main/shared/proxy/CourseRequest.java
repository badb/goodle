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
	
	Request<CourseProxy> newCourse();
	InstanceRequest<CourseProxy, Long> persist();
	InstanceRequest<CourseProxy, CourseProxy> update();
	InstanceRequest<CourseProxy, Void> remove();
	InstanceRequest<CourseProxy, Boolean> addDescription(String description);
	InstanceRequest<CourseProxy, Boolean> addBibliography(String bibliography);
	
	Request<CourseProxy> findCourse(Long id);
	Request<List<CourseProxy>> findCoursesByName(String name);
	Request<List<CourseProxy>> getAllCourses();
	
	InstanceRequest<CourseProxy, Boolean> registerCurrentUser(String key);
	InstanceRequest<CourseProxy, Boolean> unregisterUsers(List<Long> ids);
	
	InstanceRequest<CourseProxy, List<ModuleProxy>> getModulesSafe();
	InstanceRequest<CourseProxy, Boolean> updateModules(List<ModuleProxy> modules);


}
