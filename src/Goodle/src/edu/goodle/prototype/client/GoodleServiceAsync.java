package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.goodle.prototype.shared.UsosGetCoursesApiResponse;

public interface GoodleServiceAsync {

        void authorisePinCode(String sessionID, String PinCode,
                        AsyncCallback<Boolean> callback);
        void loginUser(String userName, String passwd,
                        AsyncCallback<String> callback);
        void logoutUser(String sessionID, AsyncCallback<Void> callback);
        void getCourses(String sessionID,
				AsyncCallback<UsosGetCoursesApiResponse> callback);
        void getAllCourses(String sessionID, AsyncCallback<String> callback);
        void searchCourse(String sessionID, String searchText,
                AsyncCallback<String> callback);
        void getCourseInfo(String sessionID, String courseID, AsyncCallback<String>
                callback);
}
