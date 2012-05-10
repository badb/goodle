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
	InstanceRequest<CourseProxy, Void> addTeacher(GoodleUserProxy teacher);
	InstanceRequest<CourseProxy, Void> removeTeacher(GoodleUserProxy teacher);
	InstanceRequest<CourseProxy, Void> addModule(ModuleProxy module);
	InstanceRequest<CourseProxy, Void> removeModule(ModuleProxy module);
	InstanceRequest<CourseProxy, Void> addMessage(MessageProxy message);
	InstanceRequest<CourseProxy, Void> removeMessage(MessageProxy message);
	
	InstanceRequest<CourseProxy, Long> persist();
	InstanceRequest<CourseProxy, Void> remove();
	Request<CourseProxy> findCourse(Long id);
	Request<List<CourseProxy>> findCoursesByName(String name);
}
