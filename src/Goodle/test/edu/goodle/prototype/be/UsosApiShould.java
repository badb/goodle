/**
 * 
 */
package edu.goodle.prototype.be;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.appengine.api.datastore.Email;

import edu.goodle.prototype.db.DbApi;
import edu.goodle.prototype.db.GoodleUser;
import edu.goodle.prototype.server.usosapi.UsosApiService;
import edu.goodle.prototype.shared.UsosApiResponseStatus;
import edu.goodle.prototype.shared.UsosGetCoursesApiResponse;
import edu.goodle.prototype.shared.UsosSearchCourseResponse;
import static org.mockito.Mockito.*;

public class UsosApiShould {
	DbApi mDB = null;
	UsosApiService service = null;
	GoodleUser userInDB = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mDB = mock(DbApi.class); 
		service = new UsosApiService(mDB);
		Mockito.doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		          Object[] args = invocation.getArguments();
		          userInDB = ((GoodleUser) args[0]);
		          return null;
		      }}).when(mDB).modifyUser(any(GoodleUser.class));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		service = null;
		mDB = null;
	}

	@Test
	public void test() {
		userInDB = new GoodleUser("janke", "test", "Janek", "Janke", new Email("lhoirt@gmail.com")); 
		
		UsosGetCoursesApiResponse r = service.getAllUserCourses(userInDB);
		System.out.println(r.getAuth_url());
		
		assertEquals(UsosApiResponseStatus.AUTH_REQUIRED, r.getStatus());
			
	}
	@Test
	public void searchCourseByID() {
		userInDB = new GoodleUser("janke", "test", "Janek", "Janke", new Email("lhoirt@gmail.com")); 
		
		UsosSearchCourseResponse r = service.searchCourseByID("1000-212bMD");
		
		assertEquals(UsosApiResponseStatus.OK, r.getStatus());
			
	}
	
	

}
