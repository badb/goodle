package edu.goodle.prototype.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Material {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    private GUser author;
    public GUser getAuthor() { return author; }
    
    // edit
    
    private Date created = new Date();
    public Date getCreated() { return created; }
    
    private Date modified;
    public Date getModified() { return modified; }
    
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }
    
    public Material() { }

    public Material(String name, GUser author) 
    { 
    	this.name = name;
    	this.author = author; 
    }
}
