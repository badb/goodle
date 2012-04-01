package main.shared.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Key;

@Entity
public class Mark implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private int val;
    public int getVal() { return val; }
    public void setVal(int val) { this.val = val; }
    
    private GoodleUser teacher;
    public GoodleUser getTeacher() { return teacher; }
    
    private Text comment;
    public Text getComment() { return comment; }
    public void setComment(Text comment) { this.comment = comment; }
    
    public Mark() { }
    
    public Mark(Key key, int val, GoodleUser teacher, Text comment)
    {
    	this.key = key;
    	this.val = val;
    	this.teacher = teacher;
    	this.comment = comment;
    }
    
}
