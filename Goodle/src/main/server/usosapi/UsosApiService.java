package main.server.usosapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.client.ClientFactory;
import main.server.domain.GoodleUser;
import main.server.domain.UsosInfo;
import main.shared.LongCourseDesc;
import main.shared.ShortCourseDesc;
import main.shared.proxy.LongUSOSCourseDescProxy;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.web.bindery.requestfactory.shared.Request;

import org.apache.commons.lang.StringUtils;



public class UsosApiService 
{	
	
	static class SearchResultItem{
		public SearchResultItem(){};
		protected String course_id;
		public String getID(){return course_id;}
	};
	
	static class SearchResults{
		public SearchResults(){};
		protected List<SearchResultItem> items;
		protected Boolean next_page;
		public List<SearchResultItem> getItems(){ return items;}
		public Boolean getNextPage(){ return next_page;}
	}
	
	
	public enum RequestStatus { OK, FAILED };
	
	private String baseUrl;
	private String baseUrlSecure;
	private String scope;
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	
	public UsosApiService()
	{
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

	public UsosSearchCourseResponse getCourseListById(String usosCourseID) 
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
			String response = createResponse(request);
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

	
	public Boolean generateList(List<ShortCourseDesc> listToAppend, String query, Integer size, Integer offset){
		try{
			String requestURL = baseUrl + "services/courses/search?lang=pl&name=" + 
					URLEncoder.encode(query, "utf-8") +
					"&start=" + Integer.toString(offset) +
					"&num=" + Integer.toString(size);
			URL url = new URL(requestURL);
			Logger log = Logger.getLogger("UsosApiService");
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			//consumer.sign(request);
			log.log(Level.INFO, "generateList: requestURL:" + requestURL);
			String response = createResponse(request);
			log.log(Level.INFO, "generateList: response" + response);
			// w odpowiedzi mam listę id, match, teraz muszę dla każdego ID pobrać
			// nazwę jego przedmiotu
			Gson gson = new Gson();
			SearchResults res = gson.fromJson(response, SearchResults.class);


			StringBuffer sb = new StringBuffer();
			Iterator<SearchResultItem> i = res.getItems().iterator();
			if (i.hasNext()) {
				sb.append(i.next().getID());
				while (i.hasNext())
					sb.append("|" + i.next().getID());
			}

			String requestURL1 = baseUrl + "services/courses/courses?course_ids="
					+ URLEncoder.encode(sb.toString(), "utf-8") + "&fields=";
			String encoded = "id|name";
			requestURL1 += URLEncoder.encode(encoded, "utf-8");
			URL url1 = new URL(requestURL1);
			HttpURLConnection request1 = (HttpURLConnection) url1
					.openConnection();
			request1.connect();
			log.log(Level.INFO, "generateList: requestURL:" + requestURL1);
			String response1 = createResponse(request1);
			log.log(Level.INFO, "generateList: response" + response1);
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson1 = gsonBuilder.registerTypeAdapter(ShortCourseDesc.class, new ShortCourseDeserializer()).create();
			Vector<ShortCourseDesc> v = new Vector<ShortCourseDesc>();
			JsonParser parser = new JsonParser();
		    JsonObject array = parser.parse(response1).getAsJsonObject();
		    for (Map.Entry<String, JsonElement> entry : array.entrySet()) {
	        	v.add(gson1.fromJson(entry.getValue(), ShortCourseDesc.class));
	        }
			listToAppend.addAll(v);
			return res.getNextPage();
		
			
			
		}catch (Exception e) 
		{
			System.out.println(e);
			return false;
		}
	}
	public List<ShortCourseDesc> searchCourse(String query) 
	{
		
		Integer size = 20;
		Integer offset = 0;
		List<ShortCourseDesc> list = new Vector<ShortCourseDesc>();
		while(generateList(list, query, size, offset)){
			offset += size;
		}
		return list;
	}

	private String createResponse(HttpURLConnection request) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append(line + "\n");
		}
		String response = stringBuilder.toString();
		return response;
	}
	
	public static List<ShortCourseDesc> searchCourses(String query){
		UsosApiService service = new UsosApiService();
		return service.searchCourse(query);
	}
	public static LongCourseDesc getCourseById(String courseId){
		UsosApiService service = new UsosApiService();
		return service.getCourseListById(courseId).getCourses().firstElement();
	}
}
