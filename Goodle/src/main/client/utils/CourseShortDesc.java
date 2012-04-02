package main.client.utils;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class CourseShortDesc implements Serializable
{
	private Key key;
	public Key getKey() { return key; }
	
	private String name;
	public String getName() { return name; }
	
	private String desc;
	public String getDesc() { return desc; }
	
	public CourseShortDesc() { };
	public CourseShortDesc(Key key, String name, String desc)
	{
		this.key = key;
		this.name = name;
		this.desc = desc;
	}
 
}
