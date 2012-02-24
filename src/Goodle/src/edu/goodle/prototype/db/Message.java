package edu.goodle.prototype.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class Message {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private GUser author;
    public GUser getAuthor() { return author; }
    
    private Text text;
    public Text getText() { return text; }
    public void setText(Text text) 
    {
    	this.text = text;
    	modified = new Date();
    }
    
    private Date created = new Date();
    public Date getCreated() { return created; }
    
    private Date modified;
    public Date getModified() { return modified; }
    
    public Message(Key key, GUser author, Text text)
    {
    	this.key = key;
    	this.author = author;
    	this.text = text;
    }
    
}
