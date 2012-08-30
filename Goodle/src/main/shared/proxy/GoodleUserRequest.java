package main.shared.proxy;

import java.util.List;
import java.util.Set;

import main.server.domain.GoodleUser;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(GoodleUser.class)
public interface GoodleUserRequest extends RequestContext
{
	InstanceRequest<GoodleUserProxy, Void> addCourseLed(CourseProxy course);
	InstanceRequest<GoodleUserProxy, Void> removeCourseLed(CourseProxy course);
	InstanceRequest<GoodleUserProxy, Void> addCourseAttended(CourseProxy course);
	InstanceRequest<GoodleUserProxy, Void> removeCourseAttended(CourseProxy course);
	
	InstanceRequest<GoodleUserProxy, Void> persist();
	InstanceRequest<GoodleUserProxy, Void> remove();

	InstanceRequest<GoodleUserProxy, List<CourseProxy>> getAttendedCourseProxies();
	InstanceRequest<GoodleUserProxy, List<CourseProxy>> getLedCourseProxies();
	InstanceRequest<GoodleUserProxy, List<Long>> getAttendedCourseIds();
	InstanceRequest<GoodleUserProxy, List<Long>> getLedCourseIds();
	
	Request<GoodleUserProxy> findGoodleUser(Long id);
	Request<Set<GoodleUserProxy>> findGoodleUsers(Set<Long> ids);
	Request<List<GoodleUserProxy>> findGoodleUsersByName(String name);
	Request<GoodleUserProxy> getCurrentUser();
	Request<String> getLoginUrl(String destination);
	Request<String> getLogoutUrl(String destination);
	
}
