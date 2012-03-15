package edu.goodle.prototype.db;

import com.googlecode.objectify.annotation.Entity;


@Entity
public class UploadedFile extends Material {
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public UploadedFile (String name, GoodleUser author, String url) {
		super(name, author);
		this.url = url;
	}
	
}
