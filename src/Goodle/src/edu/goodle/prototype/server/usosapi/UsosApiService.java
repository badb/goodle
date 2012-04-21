package edu.goodle.prototype.server.usosapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.goodle.prototype.db.DataModificationFailedException;
import edu.goodle.prototype.db.DbApi;
import edu.goodle.prototype.db.GoodleUser;
import edu.goodle.prototype.shared.usosapi.LongCourseDescription;
import edu.goodle.prototype.shared.usosapi.UsosApiResponseStatus;
import edu.goodle.prototype.shared.usosapi.UsosGetCoursesApiResponse;
import edu.goodle.prototype.shared.usosapi.UsosSearchCourseResponse;


public class UsosApiService {
	public enum RequestStatus{ OK, FAILED};
	private final static Logger LOGGER = Logger.getLogger(UsosApiService.class .getName());
	public UsosApiService(DbApi db){
		init(db);
	}
	public UsosApiService(){
		init(new DbApi());
	}
	
	private DbApi db= null;
	private String baseUrl=null;
	private String baseUrlSecure = null;
	private String scope = null;
	private OAuthConsumer consumer =null;
	private OAuthProvider provider = null;


	public UsosGetCoursesApiResponse getAllUserCourses(GoodleUser user) {
		UsosGetCoursesApiResponse response = null;
		try {
			if (userHasUsosData(user)) {
				String request = "request";
				String result = makeAuthorisedRequest(user, request);

				response = new UsosGetCoursesApiResponse(
						UsosApiResponseStatus.AUTH_REQUIRED);

				return createGetCoursesResponse(result);

			} else {
				response = new UsosGetCoursesApiResponse(
						UsosApiResponseStatus.AUTH_REQUIRED);
				response.setAuth_url(saveRequestTokenAndReturnAuthURL(user));
			}
		} catch (Exception e){
			clearUsosData(user);
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			LOGGER.log(Level.SEVERE, "Error getAllUserCourses for user: " + user.getLogin() +"\n" + e.toString());
		}
		/*catch (OAuthException e) {
			clearUsosData(user);
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			LOGGER.log(Level.SEVERE, "Error getAllUserCourses for user: " + user.getLogin() +"\n" + e.toString());
		} catch (DataModificationFailedException e) {
			clearUsosData(user);
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			System.out.print(e.toString());
		} catch (IOException e) {
			clearUsosData(user);
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			System.out.print(e.toString());
		}
*/
		return response;
	}

	private UsosGetCoursesApiResponse createGetCoursesResponse(String result) {
		// TODO Auto-generated method stub
		return null;
	}

	private String makeAuthorisedRequest(GoodleUser user, String request) throws IOException, OAuthException {
	    URL url = new URL(request);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    consumer.sign(conn);
	    conn.connect();
	    
	    if(conn.getResponseCode() != 200){
	    	// TODO Throw proper Excception
	    	return null;
	    	
	    }else{
	    	return getResponse(conn);
	    }
	}

	private void clearUsosData(GoodleUser user){
		try{
		user.setAccessTokenKey(null);
		user.setAccessTokenSecret(null);
		user.setRequestKey(null);
		db.modifyUser(user);
		}catch (DataModificationFailedException e) {
			LOGGER.log(Level.SEVERE, "Failed to clearUsosData for User: " + user.getLogin());
		}
		
	}

	private boolean userHasUsosData(GoodleUser user) {
		if (user.getAccessTokenKey() == null || user.getAccessTokenKey().equals(""))
			return false;
		if(user.getAccessTokenSecret() == null || user.getAccessTokenSecret().equals(""))
			return false;
		return true;
	}

	private String saveRequestTokenAndReturnAuthURL(GoodleUser user) throws OAuthException, DataModificationFailedException {
		String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);
		user.setRequestKey(consumer.getToken());
		user.setAccessTokenSecret(consumer.getTokenSecret());// todo - this information to other field
		db.modifyUser(user);
		return authUrl;
	}
	private void init(DbApi db){
		try{
		this.db = db;
		baseUrl="http://apps.usos.edu.pl/";
		baseUrlSecure = "https://apps.usos.edu.pl/";
		scope = "studies|offline_access";
		consumer = 
				new DefaultOAuthConsumer("", "");
		provider = new DefaultOAuthProvider(
				baseUrlSecure+ "services/oauth/request_token" ,
				baseUrlSecure + "services/oauth/access_token",
				baseUrl + "services/oauth/authorize");
		} catch (Error e){//UnsupportedEncodingException e) {
			throw new Error(e);
		}
	}
	public UsosSearchCourseResponse searchCourseByID(String usosCourseID) {
		try {
			String requestURL = baseUrl + "services/courses/course";
			requestURL += "?course_id=" + usosCourseID;
			requestURL += "&fields=" 
			+ URLEncoder.encode(
					"id|name|homepage_url|profile_url|is_currently_conducted|description|bibliography",
					"utf-8");
			URL url = new URL(requestURL);
			HttpURLConnection request = (HttpURLConnection) url
					.openConnection();
			consumer.sign(request);
			request.connect();
			if (request.getResponseCode() != 200) {
				throw new Exception("Response Code != 200\n Response: "
						+ request.getResponseCode() + " "
						+ request.getResponseMessage());
			}
		       
		    
		
			String response = getResponse(request);
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LongCourseDescription.class, new LongCourseDeserializer())
					.create();
			Vector<LongCourseDescription> vec = new Vector<LongCourseDescription>();
			vec.add(gson.fromJson(response, LongCourseDescription.class));
			UsosSearchCourseResponse toRet = new UsosSearchCourseResponse(UsosApiResponseStatus.OK);
			toRet.setCourses(vec);
			return toRet;
		} catch (Exception e) {
			System.out.println(e);
			return new UsosSearchCourseResponse(UsosApiResponseStatus.FAILED);

		}
		
	}
	private String getResponse(HttpURLConnection request)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();

		  String line = null;
		  while ((line = reader.readLine()) != null)
		  {
		    stringBuilder.append(line + "\n");
		  }
		return stringBuilder.toString();
	}
}
