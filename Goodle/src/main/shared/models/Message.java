package main.shared.models;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;


@Entity
public class Message extends AbstractMessage 
{

	private static final long serialVersionUID = 1L;

	public Message() { super(); }
    
    public Message(GoodleUser author, Text text) { super(author, text); }
    
}
