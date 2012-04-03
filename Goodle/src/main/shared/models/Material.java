package main.shared.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    public Material() { }

    public Material(String name, GoodleUser author) 
    { 
    	this.name = name;
    	this.author = author; 
    }
}
