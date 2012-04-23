package main.shared;

import main.shared.proxy.CourseRequest;
import main.shared.proxy.GoodleUserRequest;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface GoodleRequestFactory extends RequestFactory
{
	GoodleUserRequest goodleUserRequest();
	CourseRequest courseRequest();
}
