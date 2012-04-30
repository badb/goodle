package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import main.shared.RegMethod;

import com.google.appengine.api.datastore.Link;

@Entity
@NamedQueries 
({
        @NamedQuery
        (
                name = "findCoursesByName",
                query = "SELECT c FROM Course c WHERE c.name LIKE :name"        
        ),
        @NamedQuery
        (       
                name = "findCoursesByTerm",
                query = "SELECT c FROM Course c WHERE c.term = :term"
        )
})
public class Course implements Serializable
{       
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
    
    @Version
    @Column(name="version")
    private Integer version;
   
    @NotNull
    private String name;

    @NotNull
    private String term;
    
    @NotNull
    private String desc;
    
    @NotNull 
    private RegMethod regMethod;
    
    @NotNull
    private String password;
    
    @Basic
    private Set<Long> teachers = new HashSet<Long>();
    
    @Basic
    private Set<Long> members = new HashSet<Long>();
    
    @Basic
    private List<Long> modules = new ArrayList<Long>();
    
    private Link calendar;
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> messages = new ArrayList<Message>();
    
    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    /* Getters and setters */
    
    public Long getId() { return id; }
    
    public Integer getVersion() { return version; }
    
    public String getName() { return name; }
    
    public String getTerm() { return term; }
    
    public String getDesc() { return desc; }
    
    public RegMethod getRegMethod() { return regMethod; }
    
    public String getPassword() { return password; }
    
    public Set<Long> getTeachers() { return Collections.unmodifiableSet(teachers); }
    
    public Set<Long> getMembers() { return Collections.unmodifiableSet(members); }
    
    public List<Long> getModules() { return Collections.unmodifiableList(modules); }

    public Link getCalendar() { return calendar; }
    
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
    
    public void setVersion(Integer version) { this.version = version; }
    
    public void setName(String name) { this.name = name; }
    
    public void setTerm(String term) { this.term = term; }
    
    public void setDesc(String desc) { this.desc = desc; }
    
    public void setRegMethod(RegMethod regMethod) { this.regMethod = regMethod; }
    
    public void setPassword(String password) { this.password = password; }
    
    public void setCalendar(Link calendar) { this.calendar = calendar; }
    
    /* Collections methods */
    
    public void addTeacher(GoodleUser teacher) { teachers.add(teacher.getId()); } 

    public void removeTeacher(GoodleUser teacher) { teachers.remove(teacher.getId()); }

    public void addMember(GoodleUser member) { members.add(member.getId()); }

    public void removeMember(GoodleUser member) { members.remove(member.getId()); }
    
    public void addModule(Module module) { modules.add(module.getId()); }
    
    public void removeModule(Module module) { modules.remove(module.getId()); }
    
    public void addMessage(Message message) { messages.add(message); }
    
    public void removeMessage(Message message) { messages.remove(message); }
    
    /* Db methods */
    
    public Long persist() 
    {
        EntityManager em = entityManager();
        try 
        { 
        	em.persist(this);
        	em.refresh(this);
        	return this.id;
        }
        finally { em.close(); }
        
    }
    
    public void remove()
    {
        EntityManager em = entityManager();
        try 
        {
                Course attached = em.find(Course.class, this.id);
                em.remove(attached); 
        }
        finally { em.close(); }
    }
    
    public static Course findCourse(Long id) 
    {
        if (id == null) { return null; }
        EntityManager em = entityManager();
        try 
        {
            Course c = em.find(Course.class, id);
            return c;
        }
        finally { em.close(); }
    }
    
    @SuppressWarnings("unchecked")
    public static List<Course> findCoursesByName(String name)
    {
        EntityManager em = entityManager();
        try
        {               

                Query q = em.createNamedQuery("findCoursesByName");
                q.setParameter("name", name + "%");
                List<Course> list = q.getResultList();
                list.size(); /* force it to materialize */ 
                return list;
        }
        catch (NoResultException e) { return null; }
        finally { em.close(); }
    }
}
