package edu.mimuw.goodle.server;

import java.util.List;


public interface GoodleUserDAO {
	void addGoodleUser(GoodleUser user);
	void removeGoodleUser(GoodleUser user);
	void updateGoodleUser(GoodleUser user);
	List<GoodleUser> listGoodleUser();

	GoodleUser getUserByLogin(String login);
	GoodleUser getUserByID(Long Id);

}
