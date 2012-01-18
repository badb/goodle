package edu.goodle.prototype.be;

import java.util.Random;
import java.util.logging.Logger;

import edu.goodle.prototype.server.GoodleSession;
import edu.goodle.prototype.server.GoodleSessionDAO;
import edu.goodle.prototype.server.GoodleUser;
import edu.goodle.prototype.server.GoodleUserDAO;


public class UserDB {

	private GoodleUserDAO userDAO;
	private GoodleSessionDAO sessionDAO;

	public UserDB(GoodleUserDAO uDAO, GoodleSessionDAO sDAO) {
		userDAO = uDAO;
		sessionDAO = sDAO;
	}

	public String loginUser(String userName, String passwd) {
		GoodleUser user;
		Long sessionID;
		try {
			user = userDAO.getUserByLogin(userName);
		} catch (IndexOutOfBoundsException e) {
			Logger.getLogger("").severe("wrong login credentials");
			return null; 
		}
		if(user == null){
			Logger.getLogger("").severe("wrong login credentials");
			return null;
		}
		if (user.getPassword().equals(passwd)) {
			Random randGen = new Random();
			sessionID = randGen.nextLong();
			Logger.getLogger("")
					.info("SERVER: returned sessionID:"
							+ Long.toHexString(sessionID));

			GoodleSession newSession = new GoodleSession();
			newSession.setId(sessionID);
			newSession.setUser(user.getKey());
			sessionDAO.addGoodleSession(newSession);

		} else {
			Logger.getLogger("").severe("wrong login credentials");
			return null;
		}
		return Long.toHexString(sessionID);
	}

}
