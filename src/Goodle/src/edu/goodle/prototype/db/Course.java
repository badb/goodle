package edu.google.prototype.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;

@Entity
public class Course {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    // rocznik
    
    // avatar
    
    private Text desc;
    public Text getDesc() { return desc; }
    public void setDesc(Text desc) { this.desc = desc; }
    
    private Link site;
    public Link getSite() { return site; }
    public void setSite(Link site) { this.site = site; }
    
    private Set<GUser> teachers;
    public Set<GUser> getTeachers() { return teachers; }
    
    private Set<GUser> members;
    public Set<GUser> getMembers() { return members; }
    
    private List<Module> modules;
    public List<Module> getModules() { return modules; }
    
    private Link calendar;
    public Link getCalendar() { return calendar; }
    
    // ankiety
    
    private List<Message> messages = new ArrayList<Message>();
    public List<Message> getMessages() { return messages; }

    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }
    
    public Course(String name, Text desc, Link site, Collection<GUser> teachers, Collection<GUser> members, Link calendar)
    {
    	this.name = name;
    	this.desc = desc;
    	this.site = site;
    	this.teachers = new HashSet<GUser>(teachers);
    	this.members = new HashSet<GUser>(members);
    	this.modules = new ArrayList<Module>();
    }
    
}
