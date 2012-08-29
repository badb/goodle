package main.shared.proxy;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(UploadedFile.class)
public interface UploadedFileRequest extends RequestContext 
{
	Request<UploadedFileProxy> findUploadedFile(Long id);
}
