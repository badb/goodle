package edu.goodle.prototype.shared;

import java.io.Serializable;

public class UsosApiResponse implements Serializable {
	private UsosApiResponseStatus status;
	private String auth_url;
	protected UsosApiResponse(){}
	
	public void setAuth_url(String auth_url) {
		this.auth_url = auth_url;
	}

	public UsosApiResponse(UsosApiResponseStatus status){
		this.status = status;
		auth_url= "";
	}

	public UsosApiResponseStatus getStatus() {
		return status;
	}

	public String getAuth_url() {
		return auth_url;
	}
}
