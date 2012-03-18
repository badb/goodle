package edu.goodle.prototype.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.goodle.prototype.client.GoodleService;
import edu.goodle.prototype.db.DbApi;
import edu.goodle.prototype.shared.UsosApiResponseStatus;
import edu.goodle.prototype.shared.UsosGetCoursesApiResponse;

@SuppressWarnings("serial")
public class GoodleServiceImpl extends RemoteServiceServlet implements GoodleService {
        public Boolean authorisePinCode(String sessionID, String PinCode) {
                return new Boolean(false);
        }

        public String loginUser(String name, String password) {
        	DbApi dbApi = new DbApi();
        	return dbApi.loginUser(name, password);
        }

        @Override
        public void logoutUser(String sessionID) { }

        @Override
        public UsosGetCoursesApiResponse getCourses(String sessionID) {
                return new UsosGetCoursesApiResponse(UsosApiResponseStatus.AUTH_REQUIRED);
        }

        @Override
        public String getAllCourses(String sessionID) {
                return "This is getAllCourses(" + sessionID +")";
        }

        @Override
        public String searchCourse(String sessionID, String searchText) {
                return "This is searchCourses1. " + searchText + " | This is searchCourses2. " + searchText;
        }

        @Override
        public String getCourseInfo(String sessionID, String courseID) {
                return courseID;
        }
}
