package main.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.google.appengine.api.blobstore.BlobKey;

@SuppressWarnings("serial")
@Entity
public class UploadedFile implements Serializable
{
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() { return id; }
	// There is an error in GWT, and dummy setter is required to avoid it.
	public void setId(Long val) { } 
    
    @Version
    @Column(name="version")
    private Integer version;
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    
	@NotBlank
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @NotNull 
    private Long author;
    public Long getAuthor() { return author; }
    public void setAuthor(Long id) { author = id; }
    
    @Transient
    private String authorName;
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String name) { authorName = name; }
    
    @Basic
    @Past
    private Date uploaded = new Date();
    public Date getUploaded() { return uploaded; }
    // There is an error in GWT, and dummy setter is required to avoid it.
    public void setUploaded(Date d) { }
  
    @NotNull
    private String url;
    public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public BlobKey getKey() 
	{
		String key = url.split("blob-key=")[1];
		return new BlobKey(key);
	}
	
    
    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    /* public Long persist() 
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
    		UploadedFile attached = em.find(UploadedFile.class, this.id);
    		em.remove(attached); 
    	}
    	finally { em.close(); }
    } */

    public static UploadedFile findUploadedFile(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		UploadedFile m = em.find(UploadedFile.class, id);
    		return m;
    	}
    	finally { em.close(); }
    }
}
