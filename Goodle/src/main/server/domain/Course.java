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
import javax.validation.constraints.Pattern;

import main.shared.JoinMethod;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@SuppressWarnings("serial")
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
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    public Long getId() { return id; }
    
    @Version
    @Column(name="version")
    private Integer version;
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    
    @NotBlank
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @NotBlank
    @Pattern(regexp = "\\d{4}[LZ]?")
    private String term;
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    
    @NotNull
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @NotNull
    private String bibliography;
    public String getBibliography() { return bibliography; }
    public void setBibliography(String bibliography) { this.bibliography = bibliography; }
    
    @NotNull 
    private JoinMethod joinMethod;
    public JoinMethod getJoinMethod() { return joinMethod; }
    public void setJoinMethod(JoinMethod joinMethod) { this.joinMethod = joinMethod; }
    
    private String key;
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    
    @NotBlank
    @URL
    private String calendar;
    public String getCalendar() { return calendar; }
    public void setCalendar(String calendar) { this.calendar = calendar; }

    @Basic
    private Set<Long> coordinators = new HashSet<Long>();
    public Set<Long> getCoordinators() { return Collections.unmodifiableSet(coordinators); }
    public void addCoordinator(Long id) { coordinators.add(id); }
    public void removeCoordinator(Long id) { coordinators.remove(id); }
    
    @Basic
    private List<Long> groups = new ArrayList<Long>();
    public List<Long> getGroups() { return Collections.unmodifiableList(groups); }
    public void addGroup(Long id) { groups.add(id); }
    public void removeGroup(Long id) { groups.remove(id); }
       
    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> messages = new ArrayList<Message>();
    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
    public void addMessage(Message message) { messages.add(message); }
    public void removeMessage(Message message) { messages.remove(message); }
    
    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
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
