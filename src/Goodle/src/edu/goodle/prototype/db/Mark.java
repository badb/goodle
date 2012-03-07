package edu.goodle.prototype.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Key;

@Entity
public class Mark {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private int val;
    public int getVal() { return val; }
    public void setVal(int val) { this.val = val; }
    
    private GUser teacher;
    public GUser getTeacher() { return teacher; }
    
    private Text comment;
    public Text getComment() { return comment; }
    public void setComment(Text comment) { this.comment = comment; }
    
    public Mark() { }
    
    public Mark(Key key, int val, GUser teacher, Text comment)
    {
    	this.key = key;
    	this.val = val;
    	this.teacher = teacher;
    	this.comment = comment;
    }
    
}
