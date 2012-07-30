package main.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


@SuppressWarnings("serial")
@Embeddable
public abstract class UploadedFile implements Serializable
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
    public String getId() { return id; }
    
    @Version
    @Column(name="version")
    private Integer version;
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    
	@NotBlank
    private String name;
    public String getName() { return name; }
    public void setName(String name) 
    { 
    	if (this.name != name)
    	{
    		this.name = name; 
    		modified = new Date();
    	}
	}

    @NotNull
    private GoodleUser author;
    public GoodleUser getAuthor() { return author; }
    public void setAuthor(GoodleUser author) { this.author = author; }
    
    @Basic
    @Past
    private Date created = new Date();
    public Date getCreated() { return created; }
   
    @Basic
    @Past
    protected Date modified = new Date();
    public Date getModified() { return modified; }
    
    @NotBlank
    @URL
    private String url;
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	//@Parent Module module;
    
    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }

    public String persist() 
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
    }

    public static UploadedFile findUploadedFile(String id) 
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
