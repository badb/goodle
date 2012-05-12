package main.shared.proxy;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(UploadedFile.class)
public interface UploadedFileRequest extends MaterialRequest
{
	Request<UploadedFileProxy> findUploadedFile(Long id);
}
