package main.shared.proxy;

import main.server.domain.HomeworkFile;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(HomeworkFile.class)
public interface HomeworkFileProxy extends UploadedFileProxy
{
	MarkProxy getMark();
	
	void setMark(MarkProxy mark);

}
