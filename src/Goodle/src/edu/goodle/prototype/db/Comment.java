package edu.goodle.prototype.db;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;

@Entity
public class Comment extends AbstractMessage{
	
    public Comment() { super(); }
    
    public Comment(GoodleUser author, Text text) { super(author, text); }
    
}