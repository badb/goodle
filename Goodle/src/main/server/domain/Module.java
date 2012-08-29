package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SuppressWarnings("serial")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("MODULE")
public class Module implements Serializable 
{

	@Id
	@Column(name="module_id")
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
    
    @Basic
    private List<Long> attachedFilesIds = new ArrayList<Long>();
    public List<Long> getAttachedFilesIds() { return Collections.unmodifiableList(attachedFilesIds); }
    public void addAttachedFileId(Long id) { attachedFilesIds.add(id); }
    public void removeAttachedFileId(Long id) { attachedFilesIds.remove(id); }
    
    @Transient
    private List<UploadedFile> attachedFiles = new ArrayList<UploadedFile>();
    public List<UploadedFile> getAttachedFiles() { return attachedFiles; }
    public void setAttachedFiles(List<UploadedFile> files) { attachedFiles = files; }

/*  
 * 	Not implemented after all.
 *   
 * 	@Basic
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="module")
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }
    public void setComments(List<Message> comments) { this.comments = comments; }
 
 */

    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    public void persist() 
    {
    	EntityManager em = entityManager();
    	try 
    	{ 
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
        	Module attached = em.find(Module.class, this.id);
        	em.remove(attached); 
        }
        finally { em.close(); }
    }
    
    public static Module findModule(Long id) 
    {
        if (id == null) { return null; }
        EntityManager em = entityManager();
        try 
        {
            Module m = em.find(Module.class, id);
            return m;
        }
        finally { em.close(); }    	
    }
}
