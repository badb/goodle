package main.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientFile implements Serializable 
{
	private String name;
	private String url;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
}
