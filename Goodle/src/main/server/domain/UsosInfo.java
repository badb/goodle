package main.server.domain;

public class UsosInfo 
{
	private String accessTokenKey;

	private String accessTokenSecret; 

	private String requestKey;
	
	public String getAccessTokenKey() { return accessTokenKey; }

	public String getAccessTokenSecret() { return accessTokenSecret; }

	public String getRequestKey() { return requestKey; }
	
	public void setAccessTokenKey(String token) { accessTokenKey = token; }

	public void setAccessTokenSecret(String token) { accessTokenSecret = token; }

	public void setRequestId(String requestKey) { this.requestKey = requestKey; }

}
