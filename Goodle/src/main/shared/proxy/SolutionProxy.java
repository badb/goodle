package main.shared.proxy;

import main.server.domain.Solution;

import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(Solution.class)
public interface SolutionProxy extends UploadedFileProxy {
	String getComment();
	Float getMark();
	Boolean isChecked();
	
	void setComment(String comment);
	void setMark(Float mark);
	void setChecked(Boolean checked);
}
