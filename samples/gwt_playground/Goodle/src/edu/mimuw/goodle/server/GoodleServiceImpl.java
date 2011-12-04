package edu.mimuw.goodle.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.mimuw.goodle.client.GoodleService;

@SuppressWarnings("serial")
public class GoodleServiceImpl extends RemoteServiceServlet implements GoodleService {

	@Override
	public String getCourses(String sessionID) {
		return "This is getCourses(" + sessionID +")";
	}

	@Override
	public Boolean authorisePinCode(String sessionID, String PinCode) {
		return new Boolean(false);
	}

	@Override
	public String loginUser(String userName, String passwd) {
		// TODO Auto-generated method stub
		return "SomeSessionID";
	}

	@Override
	public void logoutUser(String sessionID) {
		;

	}

}
