package edu.goodle.prototype.server.usosapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

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
import edu.goodle.prototype.shared.LongCourseDescription;
import edu.goodle.prototype.shared.UsosApiResponseStatus;
import edu.goodle.prototype.shared.UsosGetCoursesApiResponse;
import edu.goodle.prototype.shared.UsosSearchCourseResponse;


public class UsosApiService {
	public enum RequestStatus{ OK, FAILED};
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
				if (getResultStatus(result) != RequestStatus.OK) {
					clearUsosData(user);
					response = new UsosGetCoursesApiResponse(
							UsosApiResponseStatus.AUTH_REQUIRED);

					response.setAuth_url(saveRequestTokenAndReturnAuthURL(user));

				} else {
					return createGetCoursesResponse(result);
				}
			} else {
				response = new UsosGetCoursesApiResponse(
						UsosApiResponseStatus.AUTH_REQUIRED);
				response.setAuth_url(saveRequestTokenAndReturnAuthURL(user));
			}
		} catch (OAuthException e) {
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			System.out.print(e.toString());
		} catch (DataModificationFailedException e) {
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			System.out.print(e.toString());
		} catch (IOException e) {
			response = new UsosGetCoursesApiResponse(UsosApiResponseStatus.FAILED);
			System.out.print(e.toString());
		}

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
	    	return conn.getResponseMessage();
	    }
	}

	private RequestStatus getResultStatus(String result) {
		// TODO Auto-generated method stub
		return null;
	}

	private void clearUsosData(GoodleUser user) throws DataModificationFailedException {
		user.setAccessTokenKey(null);
		user.setAccessTokenSecret(null);
		user.setRequestKey(null);
		db.modifyUser(user);
		
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();

		      String line = null;
		      while ((line = reader.readLine()) != null)
		      {
		        stringBuilder.append(line + "\n");
		      }
		       
		    
		
			String response = stringBuilder.toString();
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
}
