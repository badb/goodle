package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
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
    @Basic
    private List<String> description = new ArrayList<String>();
    public List<String> getDescription() { return description; }
    public void setDescription(List<String> description){ this.description = description;}
    
    @NotNull
    @Basic
    private List<String> bibliography = new ArrayList<String>();
    public List<String> getBibliography() { return bibliography; }
    public void setBibliography(List<String> bibliography){ this.bibliography = bibliography;}
    
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
    
//    public Course changeCourseInfo(String description, String bibliography)
//    {
//    	EntityManager em = entityManager();
//    	try
//    	{
//            Course c = em.find(Course.class, this.id);
//            if (bibliography != null)
//            	c.bibliography = new Text(bibliography);
//            if (description != null) 
//            	c.description = new Text(description);
//    		em.merge(c);
//            return c;
//    	}
//    	finally { em.close(); }
//    }
    
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
    		em.refresh(this);
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
    			for (Long homeworkId : c.homeworks)
    			{
    				Homework h = em.find(Homework.class, homeworkId);
    				for (Long solutionId : h.getSolutionsIds())
    				{
    					Solution s = em.find(Solution.class, solutionId);
    					if (s.getAuthor() == userId)
    					{
    						em.remove(s);
    						break;
    					}
    				}
    			}
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

    			if (m.getIsVisible() || owner)
    			{
    				List<UploadedFile> files = new ArrayList<UploadedFile>();
    				for (Long i : m.getAttachedFilesIds())
    				{
    					UploadedFile f = em.find(UploadedFile.class, i);
    					files.add(f);
    				}
    				m.setAttachedFiles(files);
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
    			if (h.getIsVisible() || owner)
    			{
        			List<UploadedFile> files = new ArrayList<UploadedFile>();
        			List<Solution> solutions = new ArrayList<Solution>();
        			Solution mySolution = null;
    				for (Long i : h.getAttachedFilesIds())
    				{
    					UploadedFile f = em.find(UploadedFile.class, i);
    					files.add(f);
    				}
    				if (owner)
    				{
	    				for (Long i : h.getSolutionsIds())
	    				{
	    					Solution f = em.find(Solution.class, i);
	    					GoodleUser author = em.find(GoodleUser.class, f.getAuthor());
	    					if (u.getStudentId() == null)
	    					{
	    						f.setAuthorName(author.getFirstName() + " " + author.getLastName()
	    								+ " (" + u.getEmail() + ")");
	    					}
	    					else
	    					{
	    						f.setAuthorName(author.getFirstName() + " " + author.getLastName()
	    								+ " (" + u.getStudentId() + ")");
	    					}
	    					solutions.add(f);
	    				}
    				}
    				else
    				{
	    				for (Long i : h.getSolutionsIds())
	    				{
	    					Solution f = em.find(Solution.class, i);
	    					if (f.getAuthor().equals(u.getId()))
	    					{
	    						mySolution = f;
		    					f.setAuthorName(u.getFirstName() + " " + u.getLastName()
		    							+ " (" + u.getEmail() + ")");
	    						break;
	    					}
	    				}
	    				if (mySolution != null)
	    					solutions.add(mySolution);
    				}
        			h.setSolutions(solutions);
        			h.setAttachedFiles(files);
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
    			List<UploadedFile> attachedFiles = m.getAttachedFiles();
    			if (attachedFiles != null)
    			{
    				for (UploadedFile f : attachedFiles)
    				{
    					f.setAuthor(u.getId());
    					em.persist(f);
    					em.refresh(f);
    					m.addAttachedFileId(f.getId());
    				}
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
    				h.setCourse(id);
    			}
    			List<UploadedFile> attachedFiles = h.getAttachedFiles();
    			if (attachedFiles != null)
    			{
    				for (UploadedFile f : attachedFiles)
    				{
    					f.setAuthor(u.getId());
    					em.persist(f);
    					em.refresh(f);
    					h.addAttachedFileId(f.getId());
    				}
    			}
    			
    			/*
    			 *  Autosetting version does not work for homework. Maybe inheritance is the problem. 
    			 */
    			if (h.getVersion() == null)
    				h.setVersion(1);
    			else
    				h.setVersion(h.getVersion() + 1);
    			
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
    
    public static void updateHomeworkMarks(Long courseId, Homework homework)
    {
    	List<Solution> solutions = homework.getSolutions();
    	
    	GoodleUser u = GoodleUser.getCurrentUser();
    	if (u == null) return;
    	    	
    	EntityManager em = entityManager();
    	try
    	{
    		homework = em.find(Homework.class, homework.getId());
    		Course c = em.find(Course.class, courseId);
    		
        	if (!c.coordinators.contains(u.getId())) return;
			
			if (!c.homeworks.contains(homework.getId())) return;
    		
			// For some reason solutions ids keep disappearing during
			// the RequestFactory transport. Since an url is also
			// unique among files, we try to find the valid solution in the db.
			Query q = em.createNamedQuery("findSolutionByUrl");
			
    		for (Solution s : solutions)
    		{
    			q.setParameter("url", s.getUrl());
    			Solution oldVersion = (Solution) q.getResultList().get(0);
    			
    			if (homework.getSolutionsIds().contains(oldVersion.getId()))
    			{
    				oldVersion.setComment(s.getComment());
    				oldVersion.setMark(s.getMark());
    				if (oldVersion.getMark() != null)
    					oldVersion.setChecked(true);
    				else
    					oldVersion.setChecked(false);
    			}
    			if (oldVersion.getVersion() == null)
    				oldVersion.setVersion(1);
    			else
    				oldVersion.setVersion(oldVersion.getVersion() + 1);
        		em.persist(oldVersion);
    		}
    		em.persist(homework);
    		em.persist(c);
    	}
    	finally { em.close(); }
    }
    
        
    public static void uploadSolution(Long courseId, Long homeworkId, Solution file)
    {
    	
    	EntityManager em = entityManager();
    	try
    	{
    		Course c = em.find(Course.class, courseId);
    		GoodleUser u = GoodleUser.getCurrentUser();
    		
        	if (u == null) return;
        	
        	if (!c.members.contains(u.getId())) return;
    		
    		if (!c.homeworks.contains(homeworkId)) return;
    		
    		Homework h = em.find(Homework.class, homeworkId);
    		
    		// Every student is limited to only one solution. 
    		// If previous one is already marked, ignore new one.
    		// Otherwise, remove older solution.

    		Long old = null;
    		for (Long id : h.getSolutionsIds())
    		{
    			Solution f = em.find(Solution.class, id);
    			if (f.getAuthor().equals(u.getId()))
    			{
    				if (f.isChecked()) return; 
    					
    				old = f.getId();
    				em.remove(f);
    				break;
    			}
    		}
    		if (old != null)
    		{
    			h.removeSolutionId(old);
    		}
    			
    		
    		file.setAuthor(u.getId());
    		if (file.getVersion() == null)
    			file.setVersion(1);
    		else
    			file.setVersion(file.getVersion() + 1);
    		em.persist(file);
    		em.refresh(file);
    		h.addSolutionId(file.getId());
    		em.persist(h);
    	}
    	finally { em.close(); }
    }
    
    
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
