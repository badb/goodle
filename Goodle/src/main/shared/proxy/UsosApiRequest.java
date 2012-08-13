package main.shared.proxy;

import java.util.List;

import main.server.usosapi.UsosApiService;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(UsosApiService.class)
public interface UsosApiRequest extends RequestContext {

	Request<List<ShortUSOSCourseDescProxy>> searchCourses(String query);
	Request<LongUSOSCourseDescProxy> getCourseById(String courseId);

}
