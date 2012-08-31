package main.shared.proxy;

import java.util.List;

import main.server.domain.Course;
import main.server.domain.Homework;

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
	//InstanceRequest<CourseProxy, CourseProxy> changeCourseInfo(String description, String bibliography);
	//InstanceRequest<CourseProxy, List<String> > getDescription();
	//InstanceRequest<CourseProxy, ListString> getBibliography();
	
	Request<CourseProxy> findCourse(Long id);
	Request<List<CourseProxy>> findCoursesByName(String name);
	Request<List<CourseProxy>> getAllCourses();
	Request<List<String>> getDataForSuggestBox();
	Request<List<HomeworkProxy>> findUserHomeworks(List<Long> ids);
	
	InstanceRequest<CourseProxy, Boolean> registerCurrentUser(String key);
	InstanceRequest<CourseProxy, CourseProxy> unregisterUsers(List<Long> ids);
	
	InstanceRequest<CourseProxy, List<ModuleProxy>> getModulesSafe();
	InstanceRequest<CourseProxy, List<HomeworkProxy>> getHomeworksSafe();
	InstanceRequest<CourseProxy, CourseProxy> updateModules(List<ModuleProxy> modules);
	InstanceRequest<CourseProxy, CourseProxy> updateHomeworks(List<HomeworkProxy> homeworks);
	Request<Void> uploadSolution(Long courseId, Long homeworkId, SolutionProxy file);
	
	InstanceRequest<CourseProxy, Void> addHomeworkMarks(Long homeworkId, List<SolutionProxy> solutions);    

}
