package main.shared.usos;

import java.io.Serializable;


@SuppressWarnings("serial")
public class UsosResponse implements Serializable 
{
	
	private UsosResponseStatus status;
	private String authUrl;
	
	protected UsosResponse() { }

	public UsosResponse(UsosResponseStatus status)
	{
		this.status = status;
		this.authUrl= "";
	}

	public UsosResponseStatus getStatus() { return status; }
	public String getAuthUrl() { return authUrl; }
	public void setAuthUrl(String authUrl) { this.authUrl = authUrl; }
	
}
