package main.shared.proxy;

import main.server.domain.UploadedFile;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(UploadedFile.class)
public interface UploadedFileProxy extends MaterialProxy
{
	String getUrl();
	
	void setUrl(String url);
}
