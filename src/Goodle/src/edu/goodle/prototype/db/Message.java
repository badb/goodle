package edu.goodle.prototype.db;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;

@Entity
public class Message extends AbstractMessage{
	
    public Message() { super(); }
    
    public Message(GoodleUser author, Text text) { super(author, text); }
    
}
