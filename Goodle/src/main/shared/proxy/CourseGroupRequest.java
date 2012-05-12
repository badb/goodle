package main.shared.proxy;

import main.server.domain.CourseGroup;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(CourseGroup.class)
public interface CourseGroupRequest extends RequestContext
{
	InstanceRequest<CourseGroupProxy, Void> addTeacher(Long id);
	InstanceRequest<CourseGroupProxy, Void> removeTeacher(Long id);
	InstanceRequest<CourseGroupProxy, Void> addMember(Long id);
	InstanceRequest<CourseGroupProxy, Void> removeMember(Long id);
	InstanceRequest<CourseGroupProxy, Void> addModule(Long id);
	InstanceRequest<CourseGroupProxy, Void> removeModule(Long id);
	
	InstanceRequest<CourseGroupProxy, Long> persist();
	InstanceRequest<CourseGroupProxy, Void> remove();
	Request<CourseGroupProxy> findCourseGroup(Long id);
	
	

}
