package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("goodle")
public interface GoodleService extends RemoteService {
	String getCourses(String sessionID);
	Boolean authorisePinCode(String sessionID, String PinCode);
	String loginUser(String userName, String passwd);
	void logoutUser(String sessionID);
}
