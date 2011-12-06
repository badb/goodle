package edu.goodle.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GoodleServiceAsync {

	void authorisePinCode(String sessionID, String PinCode,
			AsyncCallback<Boolean> callback);

	void getCourses(String sessionID, AsyncCallback<String> callback);

	void loginUser(String userName, String passwd,
			AsyncCallback<String> callback);

	void logoutUser(String sessionID, AsyncCallback<Void> callback);

}
