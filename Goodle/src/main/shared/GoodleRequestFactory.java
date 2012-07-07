package main.shared;

import main.shared.proxy.CourseRequest;
import main.shared.proxy.GoodleUserRequest;
import main.shared.proxy.HomeworkFileRequest;
import main.shared.proxy.HomeworkRequest;
import main.shared.proxy.MaterialRequest;
import main.shared.proxy.ModuleRequest;
import main.shared.proxy.UploadedFileRequest;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface GoodleRequestFactory extends RequestFactory
{
	GoodleUserRequest goodleUserRequest();
	CourseRequest courseRequest();
	HomeworkFileRequest homeworkFileRequest();
	HomeworkRequest homeworkRequest();
	MaterialRequest materialRequest();
	ModuleRequest moduleRequest();
	UploadedFileRequest uploadedFileRequest();
}
