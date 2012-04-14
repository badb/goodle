package main.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

public interface GoodleUserRequest extends RequestContext
{
	InstanceRequest<GoodleUserProxy, Void> persist();
	Request<GoodleUserProxy> findCourse(Long id);
	Request<List<GoodleUserProxy>> findCoursesByName(String name);

}
