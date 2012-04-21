package main.server.domain;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;


@Entity
public class Comment extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;

	public Comment() { super(); }
    
    public Comment(GoodleUser author, Text text) { super(author, text); }
    
}