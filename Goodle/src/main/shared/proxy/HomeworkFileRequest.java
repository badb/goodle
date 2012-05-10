package main.shared.proxy;

import main.server.domain.HomeworkFile;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(HomeworkFile.class)
public interface HomeworkFileRequest extends UploadedFileRequest
{
	Request<HomeworkFileProxy> findHomeworkFile(Long id);
}
