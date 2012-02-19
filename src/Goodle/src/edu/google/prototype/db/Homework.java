package edu.google.prototype.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class Homework {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    public String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Text desc;
    public Text getDesc() { return desc; }
    public void setDesc(Text desc) { this.desc = desc; }
    
    public GUser teacher;
    public GUser getTeacher() { return teacher; }
    
    public Date created = new Date();
    public Date getCreated() { return created; }
    
    public Date modified;
    public Date getModified() { return modified; }
    
    public Date deadline;
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    
    public List<HomeworkFile> provided = new ArrayList<HomeworkFile>();
    public List<HomeworkFile> getProvided() { return provided; }
    
    public List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }
    
    public Homework(GUser teacher, String name, Text desc, Date deadline)
    {
    	this.teacher = teacher;
    	this.name = name;
    	this.desc = desc;
    	this.deadline = deadline;
    }

}
