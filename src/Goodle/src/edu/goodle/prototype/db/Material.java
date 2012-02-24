package edu.google.prototype.db;

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
    
    // widoczność
    
    public GUser author;
    public GUser getAuthor() { return author; }
    
    public Set<GUser> canEdit;
    public Set<GUser> getCanEdit() { return canEdit; }
    
    public Date created = new Date();
    public Date getCreated() { return created; }
    
    public Date modified;
    public Date getModified() { return modified; }
    
    public List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }

    public Material(GUser author) { this.author = author; }
}
