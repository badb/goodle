package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
@Entity
public class Homework extends Module
{
	/*@Id
	@Column(name="homework_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
    public Long getId() { return id; }
    
    @Version
    @Column(name="version")
    private Integer version;
	public Integer getVersion() { return version; }
	public void setVersion(Integer version) { this.version = version; }
	
	@NotBlank
    private String title;	
    public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	@NotNull
	private Long author;
	public Long getAuthor() { return author; }
	public void setAuthor(Long author) { this.author = author; }
	
	@NotNull
    private String text;	
    public String getText() { return text; }
	public void setText(String text) {this.text = text; }
    
    private boolean isVisible;
    public boolean getIsVisible() { return isVisible; }
    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    */
    /*@Basic 
    private List<UploadedFile> files = new ArrayList<UploadedFile>();
    public List<UploadedFile> getFiles() { return Collections.unmodifiableList(files);  }
    public void setFiles(List<UploadedFile> files) { 
    	EntityManager em = entityManager();
    	BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
    	try {
    		for (UploadedFile uf : this.files) {
    			if (!files.contains(uf)) {
    				UploadedFile u = em.find(UploadedFile.class, uf.getId());
    				blobstore.delete(uf.getKey());
    				em.remove(u);
    			}
    		}
    		this.files = files;
    	}
    	finally {em.close();}
    	
    }*/
    
    //public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    /*public void persist() 
    {
    	EntityManager em = entityManager();
    	try { 
    		em.persist(this); 
    		em.refresh(this);
    	}
    	finally { em.close(); }
    }
    
    public void remove()
    {
        EntityManager em = entityManager();
        try 
        {
        	Homework attached = em.find(Homework.class, this.id);
        	em.remove(attached); 
        }
        finally { em.close(); }
    }
    */

    @Basic
    @Past
    protected Date modified = new Date();
    public Date getModified() { return modified; }
    
    @Basic
    private Date deadline;
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) 
    { 
    	if (this.deadline != deadline)
    	{
    		this.deadline = deadline; 
    		modified = new Date();
    	}
    }
    
    private Long course;
    public Long getCourse() { return course; }
    public void setCourse(Long course) {
    	this.course = course;
    }
    
    public static Homework findHomework(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		Homework h = em.find(Homework.class, id);
    		return h;
    	}
    	finally { em.close(); }
    }
}

