package main.shared.models;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;

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
		name = "findCoursesDescByName",
		query = "SELECT c.key, c.name, c.desc FROM Course c WHERE c.name = :name"
	),
	@NamedQuery
	(	
		name = "findCoursesByTerm",
		query = "SELECT c FROM Course c WHERE c.term = :term"
	)
})
public class Course implements Serializable
{
	private static final long serialVersionUID = 1L;
	
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
    
    private String desc;
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    
    private Set<Key> teachers;
    public Set<Key> getTeachers() { return Collections.unmodifiableSet(teachers); }
    public void addTeacher(GoodleUser teacher) 
    { 
    	teachers.add(teacher.getKey()); 
    }
    public void removeTeacher(GoodleUser teacher) 
    { 
    	teachers.remove(teacher.getKey()); 
    }
    
    private Set<Key> members;
    public Set<Key> getMembers() { return Collections.unmodifiableSet(members); }
    public void addMember(GoodleUser member) 
    { 
    	members.add(member.getKey()); 
    }
    public void removeMember(GoodleUser member) 
    { 
    	members.remove(member.getKey()); 
    }
    
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
    
    public Course() { }
    
    public Course
    (
    		String name, 
    		String term, 
    		String desc, 
    		Collection<GoodleUser> teachers, 
    		Collection<GoodleUser> members, 
    		Link calendar
    )
    {
    	this.name = name;
    	this.term = term;
    	this.desc = desc;;
    	this.teachers = new HashSet<Key>();
    	this.modules = new ArrayList<Module>();
    	this.members = new HashSet<Key>();
    	for (GoodleUser t : teachers) { this.teachers.add(t.getKey()); };
    	for (GoodleUser m : members) { this.members.add(m.getKey()); };
    }
    
}
