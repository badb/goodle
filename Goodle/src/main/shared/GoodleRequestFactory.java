package main.shared;

import main.shared.proxy.CourseRequest;
import main.shared.proxy.GoodleUserRequest;
import main.shared.proxy.UploadedFileRequest;
import main.shared.proxy.ModuleRequest;
import main.shared.proxy.UploadedFileRequest;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface GoodleRequestFactory extends RequestFactory
{
	GoodleUserRequest goodleUserRequest();
	CourseRequest courseRequest();
	UploadedFileRequest materialRequest();
	ModuleRequest moduleRequest();
	UploadedFileRequest uploadedFileRequest();
}
