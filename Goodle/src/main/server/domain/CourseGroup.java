package main.server.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import main.shared.CourseGroupType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class CourseGroup 
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
    public String subname;
    public String getSubname() { return subname; }
    public void setSubname(String subname) { this.subname = subname; }
    
    @NotNull
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @NotNull
    private CourseGroupType groupType;
    public CourseGroupType getGroupType() { return groupType; }
    public void setGroupType(CourseGroupType groupType) { this.groupType = groupType; }
    
    @NotBlank
    @URL
    private String calendar;
    public String getCalendar() { return calendar; }
    public void setCalendar(String calendar) { this.calendar = calendar; }
    
    @Basic
    private Set<Long> teachers = new HashSet<Long>();
    public Set<Long> getTeachers() { return Collections.unmodifiableSet(teachers); }
    public void addTeacher(Long id) { teachers.add(id); } 
    public void removeTeacher(Long id) { teachers.remove(id); }
    
    @Basic
    private Set<Long> members = new HashSet<Long>();
    public Set<Long> getMembers() { return Collections.unmodifiableSet(members); }
    public void addMember(Long id) { members.add(id); }
    public void removeMember(Long id) { members.remove(id); }
    
    @Basic
    private List<Long> modules = new ArrayList<Long>();
    public List<Long> getModules() { return Collections.unmodifiableList(modules); }
    public void addModule(Long id) { modules.add(id); }
    public void removeModule(Long id) { modules.remove(id); }
       
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
                CourseGroup attached = em.find(CourseGroup.class, this.id);
                em.remove(attached); 
        }
        finally { em.close(); }
    }
    
    public static CourseGroup findCourseGroup(Long id) 
    {
        if (id == null) { return null; }
        EntityManager em = entityManager();
        try 
        {
            CourseGroup g = em.find(CourseGroup.class, id);
            return g;
        }
        finally { em.close(); }
    }
}
