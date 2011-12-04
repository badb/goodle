package edu.mimuw.goodle.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.mimuw.goodle.client.GoodleService;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class GoodleServiceImpl extends RemoteServiceServlet implements GoodleService {
	private Logger logger = Logger.getLogger("");
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
		Long sessionID=0L;
		GoodleUserDAO userDB = new GoodleUserDAOImpl();
		GoodleSessionDAO sessionDB = new GoodleSessionDAOImpl();
		// remove users
		List<GoodleUser> list = userDB.listGoodleUser();
		logger.severe("Number of existing users:" + list.size());
		for (Iterator<GoodleUser> iterator = list.iterator(); iterator
				.hasNext();) {
			GoodleUser goodleUser = iterator.next();
			logger.severe("Removing user: Username:" + goodleUser.getLogin() + " Password:"
					+ goodleUser.getPassword());
			userDB.removeGoodleUser(goodleUser);
		}
		
		//Creating user
		try{
		String tUserName = "test";
		String tPasswd = "test";
		GoodleUser testUser = new GoodleUser();
		testUser.setLogin(tUserName);
		testUser.setPassword(tPasswd);
		userDB.addGoodleUser(testUser);
		testUser = null;
		}catch (Exception e) {
			logger.severe("failed to add user: " + e.toString());
		}
		
		
		
		
		// Logging User;
		GoodleUser user;
		try{
		 user= userDB.getUserByLogin(userName);
		}catch(IndexOutOfBoundsException e){
			logger.severe("failed to get User. UserName: " + userName);
			throw e;
		}
		if(user.getPassword().equals(passwd)){
			Random randGen = new Random();
			sessionID = randGen.nextLong();
			Logger.getLogger("").info("SERVER: returned sessionID:" + Long.toHexString(sessionID));
			
			GoodleSession newSession = new GoodleSession();
			newSession.setId(sessionID);
			newSession.setUser(user.getKey());
			sessionDB.addGoodleSession(newSession);
			
		} else {
			Logger.getLogger("").severe("SERVER: wrong passwd");
			throw new Error("Wrong password");
		}
		return Long.toHexString(sessionID);
	}

	@Override
	public void logoutUser(String sessionID) {
		;

	}

}
