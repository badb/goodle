package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("goodle")


public interface GoodleService extends RemoteService {
    Boolean authorisePinCode(String sessionID, String PinCode);
    String loginUser(String userName, String passwd);
    void logoutUser(String sessionID);

    String getCourses(String sessionID);
    String getAllCourses(String sessionID);
    String searchCourse(String sessionID, String searchText);
    String getCourseInfo(String sessionID, String courseID);	
}
