package main.shared.proxy;

import java.util.List;
import java.util.Set;

import main.server.domain.GoodleUser;
import main.shared.ShortCourseDesc;

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
	InstanceRequest<GoodleUserProxy, Void> addMessage(MessageProxy message);
	InstanceRequest<GoodleUserProxy, Void> removeMessage(MessageProxy message);
	InstanceRequest<GoodleUserProxy, Void> addFlag(String flag);
	InstanceRequest<GoodleUserProxy, Boolean> hasFlag(String flag);
	InstanceRequest<GoodleUserProxy, Void> removeFlag(String flag);
	
	InstanceRequest<GoodleUserProxy, Void> persist();
	InstanceRequest<GoodleUserProxy, Void> remove();
	Request<GoodleUserProxy> findGoodleUser(Long id);
	Request<Set<GoodleUserProxy>> findGoodleUsers(Set<Long> ids);
	Request<List<GoodleUserProxy>> findGoodleUsersByName(String name);
	Request<GoodleUserProxy> getCurrentUser();
	Request<String> getLoginUrl(String destination);
	Request<String> getLogoutUrl(String destination);
	
}
