package main.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface GoodleRequestFactory extends RequestFactory
{
	CourseRequest courseRequest();
}