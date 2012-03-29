package edu.goodle.prototype.db;

import java.io.Serializable;
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
public class Material implements Serializable
{
	private static final long serialVersionUID = 1L;
	
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
    private List<Comment> comments = new ArrayList<Comment>();
    public List<Comment> getComments() { return comments; }
    public void addComment(Comment comment) { comments.add(comment); }
    public void removeComment(Comment comment) { comments.remove(comment); }
    
    public Material() { }

    public Material(String name, GoodleUser author) 
    { 
    	this.name = name;
    	this.author = author; 
    }
}
