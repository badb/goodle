package main.server.usosapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import main.client.ClientFactory;
import main.server.domain.GoodleUser;
import main.shared.LongCourseDesc;
import main.shared.usos.UsosGetCoursesResponse;
import main.shared.usos.UsosResponseStatus;
import main.shared.usos.UsosSearchCourseResponse;
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import com.google.appengine.tools.admin.OAuth2ServerConnection.OAuthException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UsosApiService 
{	
	public enum RequestStatus { OK, FAILED };
	
	private ClientFactory clientFactory;
	private String baseUrl;
	private String baseUrlSecure;
	private String scope;
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	
	public UsosApiService(ClientFactory clientFactory)
	{
		this.clientFactory = clientFactory;
		baseUrl = "http://apps.usos.edu.pl/";
		baseUrlSecure = "https://apps.usos.edu.pl/";
		scope = "studies|offline_access";
		consumer = new DefaultOAuthConsumer("", "");
		provider = new DefaultOAuthProvider
		(
			baseUrlSecure + "services/oauth/request_token" ,
			baseUrlSecure + "services/oauth/access_token",
			baseUrl + "services/oauth/authorize"
		);
	}

	public UsosGetCoursesResponse getAllUserCourses(GoodleUser user) 
	{
		UsosGetCoursesResponse response = null;
		try 
		{
			if (userHasUsosData(user)) 
			{
				String result = makeAuthorisedRequest(user, "request");
				if (getResultStatus(result) != RequestStatus.OK) 
				{
					clearUsosData(user);
					response = new UsosGetCoursesResponse(UsosResponseStatus.AUTH_REQUIRED);
					response.setAuthUrl(saveRequestTokenAndReturnAuthURL(user));
				} 
				else response = createGetCoursesResponse(result);
			} 
			else 
			{
				response = new UsosGetCoursesResponse(UsosResponseStatus.AUTH_REQUIRED);
				response.setAuthUrl(saveRequestTokenAndReturnAuthURL(user));
			}
		} 
		catch (Exception e) 
		{
			response = new UsosGetCoursesResponse(UsosResponseStatus.FAILED);
			System.out.print(e.toString());
		} 
		return response;
	}

	private UsosGetCoursesResponse createGetCoursesResponse(String result) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	private String makeAuthorisedRequest(GoodleUser user, String request) 
			throws 
				IOException, 
				OAuthMessageSignerException, 
				OAuthExpectationFailedException, 
				OAuthCommunicationException 
	{
		URL url = new URL(request);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		consumer.sign(conn);
		conn.connect();
		if (conn.getResponseCode() != 200)
		{
			// TODO Throw proper Excception
			return null;
		}
		return conn.getResponseMessage();
	}

	private RequestStatus getResultStatus(String result) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void clearUsosData(GoodleUser user)
	{
		// TODO Dostosować do RequestFactory
	}

	private boolean userHasUsosData(GoodleUser user) 
	{
		return (user.getAccessTokenKey() == null || user.getAccessTokenKey().equals("") ||
				user.getAccessTokenSecret() == null || user.getAccessTokenSecret().equals(""));
	}

	private String saveRequestTokenAndReturnAuthURL(GoodleUser user) 
			throws OAuthException, OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
	{
		String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);


		// TODO dostosować do RequestFactory
		//user.setRequestKey(consumer.getToken());
		//user.setAccessTokenSecret(consumer.getTokenSecret());// todo - this information to other field
		//db.modifyUser(user);
		return authUrl;
	}

	public UsosSearchCourseResponse searchCourseByID(String usosCourseID) 
	{
		try 
		{
			String requestURL = baseUrl + "services/courses/course?course_id=" + usosCourseID + "&fields="; 
			String encoded = "id|name|homepage_url|profile_url|is_currently_conducted|description|bibliography";
			requestURL += URLEncoder.encode(encoded, "utf-8");
			URL url = new URL(requestURL);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			consumer.sign(request);
			request.connect();
			if (request.getResponseCode() != 200) 
			{
				throw new Exception
				(
					"Response Code != 200\n Response: "
					+ request.getResponseCode() + " "
					+ request.getResponseMessage()
				);
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				stringBuilder.append(line + "\n");
			}
			String response = stringBuilder.toString();
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.registerTypeAdapter(LongCourseDesc.class, new LongCourseDeserializer()).create();
			Vector<LongCourseDesc> v = new Vector<LongCourseDesc>();
			v.add(gson.fromJson(response, LongCourseDesc.class));
			UsosSearchCourseResponse toRet = new UsosSearchCourseResponse(UsosResponseStatus.OK);
			toRet.setCourses(v);
			return toRet;
		}
		catch (Exception e) 
		{
			System.out.println(e);
			return new UsosSearchCourseResponse(UsosResponseStatus.FAILED);
		}
	}
}
