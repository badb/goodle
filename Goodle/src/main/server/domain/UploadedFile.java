package main.server.domain;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;


@Entity
public class UploadedFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	public Long key;
	private String url;
	private String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public UploadedFile() {
		super();
		
	}
	
	public UploadedFile (String name, GoodleUser author, String url) {
		//TODO
		this.name = name;
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setKey(Long key) {
		this.key = key;
	}
	
 	public Long getKey() {
		return key;
	}
	
}
