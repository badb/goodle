package edu.goodle.prototype.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
    
    private GoodleUser author;
    public GoodleUser getAuthor() { return author; }
    
    // edit
    
    private Date created = new Date();
    public Date getCreated() { return created; }
    
    private Date modified;
    public Date getModified() { return modified; }
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }
    
    public Material() { }

    public Material(String name, GoodleUser author) 
    { 
    	this.name = name;
    	this.author = author; 
    }
}
