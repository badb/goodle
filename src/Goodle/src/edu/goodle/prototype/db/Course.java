package edu.goodle.prototype.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;

@Entity
@NamedQueries 
({
	@NamedQuery
	(
		name = "findCoursesByName",
		query = "SELECT c FROM Course c WHERE c.name = :name"	
	),
	@NamedQuery
	(	
		name = "findCoursesByTerm",
		query = "SELECT c FROM Course c WHERE c.term = :term"
	)
})
public class Course {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    private String term;
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    
    // avatar
    
    private Text desc;
    public Text getDesc() { return desc; }
    public void setDesc(Text desc) { this.desc = desc; }
    
    private Link site;
    public Link getSite() { return site; }
    public void setSite(Link site) { this.site = site; }
    
    @ManyToMany(mappedBy="coursesLed")
    private Set<GUser> teachers;
    public Set<GUser> getTeachers() { return Collections.unmodifiableSet(teachers); }
    public void addTeacher(GUser teacher) { teachers.add(teacher); }
    public void removeTeacher(GUser teacher) { teachers.remove(teacher); }
    
    @ManyToMany(mappedBy="courses")
    private Set<GUser> members;
    public Set<GUser> getMembers() { return Collections.unmodifiableSet(members); }
    public void addMember(GUser member) { members.add(member); }
    public void removeMember(GUser member) { members.remove(member); }
    
    @OneToMany
    private List<Module> modules;
    public List<Module> getModules() { return Collections.unmodifiableList(modules); }
    public void addModule(Module module) { modules.add(module); }
    public void removeModule(Module module) { modules.remove(module); }
    
    private Link calendar;
    public Link getCalendar() { return calendar; }
    
    // ankiety
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> messages = new ArrayList<Message>();
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
    public void addMessage(Message message) { messages.add(message); }
    public void removeMessage(Message message) { messages.remove(message); }

    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }
    
    public Course() { }
    
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
