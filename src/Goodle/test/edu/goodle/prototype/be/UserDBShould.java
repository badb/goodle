package edu.goodle.prototype.be;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import edu.goodle.prototype.server.GoodleSessionDAO;
import edu.goodle.prototype.server.GoodleUser;
import edu.goodle.prototype.server.GoodleUserDAO;

public class UserDBShould {
	private GoodleUserDAO uDAO;
	private GoodleSessionDAO sDAO;
	private UserDB db;
	

	private void setUp() {
		uDAO = mock(GoodleUserDAO.class);
		sDAO = mock(GoodleSessionDAO.class);
		db = new UserDB(uDAO, sDAO);
	}
	
	private void tearDown() {
		uDAO = null;
		sDAO = null;
		db = null;
	}
	
	@Test
	public void shouldNotLoginNotExistingUser() {
		setUp();
		when(uDAO.getUserByLogin("test")).thenReturn(null);
		
		String session = db.loginUser("test","test");
		
		assertNull(session);
		tearDown();
	}

	
	@Test
	public void shouldLoginExistingUser() {
		setUp();
		GoodleUser testUser = new GoodleUser();
		testUser.setLogin("test");
		testUser.setPassword("test");
		when(uDAO.getUserByLogin("test")).thenReturn(testUser);
		
		String session = db.loginUser("test","test");
		
		assertNotNull(session);
		tearDown();
	}
	
	@Test
	public void shouldNotLoginWithWrongPassword() {
		setUp();
		GoodleUser testUser = new GoodleUser();
		testUser.setLogin("test");
		testUser.setPassword("notTest");
		when(uDAO.getUserByLogin("test")).thenReturn(testUser);
		
		String session = db.loginUser("test","test");
		
		assertNull(session);
		tearDown();
	}	
}
