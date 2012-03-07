package edu.goodle.prototype.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

@Entity
public class HomeworkFile {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private GUser author;
    public GUser getAuthor() { return author; }
    
    private Date created = new Date();
    public Date getCreated() { return created; }
    
    private Mark mark;
    public Mark getMark() { return mark; }
    public void setMark(Mark mark) { this.mark = mark; }
    
    private Link file;
    public Link getFile() { return file; }
    
    public HomeworkFile() { }
    
    public HomeworkFile(GUser author, Link file)
    {
    	this.author = author;
    	this.file = file;
    }
}
