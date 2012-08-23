package main.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        ),
        @NamedQuery
        (
        		name="getAllCourses",
        		query = "SELECT c FROM Course c"
        ),
        @NamedQuery
        (
        		name="getDataForSuggestBox",
        		query = "SELECT name FROM Course"
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
    private Set<Long> members = new HashSet<Long>();
    public Set<Long> getMembers() { return Collections.unmodifiableSet(members); }
    public void addMember(Long id) { members.add(id); }
    public void removeMember(Long id) { members.remove(id); }
    
    @Basic
    private List<Long> modules = new ArrayList<Long>();
    public List<Long> getModules() { return Collections.unmodifiableList(modules); }
    public void addModule(Long id) { modules.add(id); }
    public void removeModule(Long id) { modules.remove(id); }
    
    @Basic
    private List<Long> homeworks = new ArrayList<Long>();
    public List<Long> getHomeworks() { return Collections.unmodifiableList(homeworks); }
    public void addHomework(Long id) { homeworks.add(id); }
    public void removeHomework(Long id) {homeworks.remove(id);}
    
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
	public static List<Course> findCourses(Set<Long> ids) {
    	
    	List<Course> lista = new ArrayList<Course>();
    	if (ids == null || ids.isEmpty()) { return lista; }
        EntityManager em = entityManager();
        try 
        {
        	Query q = em.createQuery(  
                    "SELECT c FROM Course c WHERE c.id IN (?1)").setParameter(1, ids);
            lista = q.getResultList();
            lista.size(); /* force it to materialize */ 
        	return lista;
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
    
    @SuppressWarnings("unchecked")
    public static List<Course> getAllCourses()
    {
        EntityManager em = entityManager();
        try
        {               
                Query q = em.createNamedQuery("getAllCourses");
                List<Course> list = q.getResultList();
                list.size(); /* force it to materialize */ 
                return list;
        }
        catch (NoResultException e) { return null; }
        finally { em.close(); }
    }

    @SuppressWarnings("unchecked")
    public static List<String> getDataForSuggestBox()
    {
        EntityManager em = entityManager();
        try
        {               
                Query q = em.createNamedQuery("getDataForSuggestBox");
                List<String> list = q.getResultList();
                list.size(); /* force it to materialize */ 
                return list;
        }
        catch (NoResultException e) { return null; }
        finally { em.close(); }
    }
    
    public static Course newCourse()
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null) return null;
    	
    	Date d = new Date();
		int year = (d.getMonth() > 5 ? d.getYear() : (d.getYear() - 1)) - 100;
    	String term = "" + year + "/" + (year + 1);
    	
    	Course c = new Course();
    	c.setVersion(1);
    	c.setName("Nowy kurs");
    	c.setTerm(term);
    	c.setJoinMethod(JoinMethod.OPEN);    	
    	c.addCoordinator(u.getId());
        EntityManager em = entityManager();
        try 
        { 
        	em.persist(c);
        	em.refresh(c);
        	u.addCourseLed(c);
        	em.persist(u);
        	return c;
        }
        finally { em.close(); }
    }
    
    public Course update()
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null) return null;
    	
    	if (!coordinators.contains(u.getId()))
    	{
    		return null;
    	}
    	
    	EntityManager em = entityManager();
    	try
    	{
    		em.persist(this);    	
        	return this;
    	}
    	finally { em.close(); }
    }
    
    public boolean registerCurrentUser(String key)
    {
    	if (joinMethod == JoinMethod.KEY && !this.key.equals(key)) return false;
    	
        GoodleUser u = GoodleUser.getCurrentUser();
        if (u == null) return false;
    	
    	EntityManager em = entityManager();
    	try
    	{
            Course c = em.find(Course.class, this.id);
    		c.addMember(u.getId());
    		u.addCourseAttended(c);
    		em.merge(c);
    		em.merge(u);
    	}
    	finally 
    	{ 
    		em.close(); 
    	}
    	
    	return true;
    }
    
    public Course unregisterUsers(Collection<Long> ids)
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null || !coordinators.contains(u.getId()))
    	{
    		return null;
    	}
    	
    	EntityManager em = entityManager();
    	try
    	{
    		Course c = em.find(Course.class, this.id);
    		for (Long userId : ids)
    		{
    			c.removeMember(userId);
    			u = em.find(GoodleUser.class, userId);
    			u.removeCourseAttended(c);
    			em.merge(u);
    			em.merge(c);
    		}
    		return c;
    	}
    	finally { em.close(); }
    }
    
    public List<Module> getModulesSafe() 
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	boolean owner = coordinators.contains(u.getId());
    	boolean member = members.contains(u.getId());
    	List<Module> l = new ArrayList<Module>();
    	
    	if (!owner && !member) return l;
    	
    	EntityManager em = entityManager();
    	try
    	{
    		for (Long id : modules)
    		{
    			Module m = em.find(Module.class, id);
    			m.getMaterials().size();
    			if (m.getIsVisible() || (!m.getIsVisible() && owner))
    			{
    				l.add(m);
    			}
    		}
    		return l;
    	}
    	finally { em.close(); }
    }
    
    public List<Homework> getHomeworksSafe() 
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	boolean owner = coordinators.contains(u.getId());
    	boolean member = members.contains(u.getId());
    	List<Homework> l = new ArrayList<Homework>();
    	
    	if (!owner && !member) return l;
    	
    	EntityManager em = entityManager();
    	try
    	{
    		for (Long id : homeworks)
    		{
    			Homework h = em.find(Homework.class, id);
    			if (h != null && (h.getIsVisible() || (!h.getIsVisible() && owner)))
    			{
    				l.add(h);
    			}
    		}
    		return l;
    	}
    	finally { em.close(); }
    }
    
    public Course updateModules(List<Module> modules)
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null) return null;
    	
    	if (!coordinators.contains(u.getId())) return null;
    	
    	List<Long> newModules = new ArrayList<Long>();
    	
    	EntityManager em = entityManager();
    	try
    	{
    		Course c = em.find(Course.class, this.id);
			u = em.merge(u);
    		
    		for (Module m : modules)
    		{
    			if (!c.modules.remove(m.getId()))
    			{
    				m.setAuthor(u.getId());
    			}
    			em.persist(m);
    			em.refresh(m);
    			newModules.add(m.getId());
    		}
    		for (Long id : c.modules)
    		{
    			Module m = em.find(Module.class, id);
    			em.remove(m);
    		}
    		c.modules = newModules;
    		em.persist(c);
        	return c;
    	}
    	finally { em.close(); }
    }
    
    public Course updateHomeworks(List<Homework> homeworks)
    {
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null) return null;
    	
    	if (!coordinators.contains(u.getId())) return null;
    	
    	List<Long> newHomeworks = new ArrayList<Long>();
    	
    	EntityManager em = entityManager();
    	try
    	{
    		Course c = em.find(Course.class, this.id);
			u = em.merge(u);
    		
    		for (Homework h : homeworks)
    		{
    			if (!c.homeworks.remove(h.getId()))
    			{
    				h.setAuthor(u.getId());
    				h.setVersion(1);
    			}
    			
    			em.persist(h);
    			em.refresh(h);
    			newHomeworks.add(h.getId());
    		}
    		for (Long id : c.homeworks)
    		{
    			Homework h = em.find(Homework.class, id);
    			em.remove(h);
    		}
    		c.homeworks = newHomeworks;
    		em.persist(c);
        	return c;
    	}
    	finally { em.close(); }
    }
    
    public boolean addDescription(String description)
    {
    	EntityManager em = entityManager();
    	try
    	{
            Course c = em.find(Course.class, this.id);
    		c.setDescription(description);
    		em.merge(c);
        	return true;
    	}
    	finally { em.close(); }
    }
    
    public boolean addBibliography(String bibliography)
    {
    	EntityManager em = entityManager();
    	try
    	{
            Course c = em.find(Course.class, this.id);
    		c.setBibliography(bibliography);
    		em.merge(c);
        	return true;
    	}
    	finally { em.close(); }	
    }
    
    /*public Module setMaterialProxies(Module module, List<UploadedFile> materials) {
    
    	module.setMaterials(materials);
    	return module;
    }
    public Module addComment(Module module, Message comment) {
    	module.addComment(comment);
    	return module;
    }*/
    
	public static List<Homework> findUserHomeworks(List<Long> ids)
	{

		List<Homework> homeworks = new ArrayList<Homework>();
		
		EntityManager em = entityManager();
		try
		{
			for(Long id : ids) {
	    		Course c = em.find(Course.class, id);
	    		if (c != null) {
	    			Date d = new Date();
	    			List <Homework> l = c.getHomeworksSafe();
	    			for(Homework h: l) {
	    				if (h!= null && (h.getDeadline() == null || h.getDeadline().compareTo(d) > 0 ))
	    					homeworks.add(h);
	    			}
	    		}
			}
			return homeworks;
		}
		finally { em.close(); }
	}
}
