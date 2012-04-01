package main.shared.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;


@MappedSuperclass
public abstract class AbstractMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private GoodleUser author;
    public GoodleUser getAuthor() { return author; }
    
    private Text text;
    public Text getText() { return text; }
    public void setText(Text text) 
    {
    	this.text = text;
    	modified = new Date();
    }
    
    private Date created = new Date();
    public Date getCreated() { return created; }
    
    private Date modified = new Date();
    public Date getModified() { return modified; }
    
    public AbstractMessage() { }
    
    public AbstractMessage(GoodleUser author, Text text)
    {
    	this.author = author;
    	this.text = text;
    }
	
}
