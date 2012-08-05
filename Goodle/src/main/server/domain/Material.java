package main.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Material implements Serializable
{
	@Id
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
    public void setName(String name) 
    { 
    	if (this.name != name)
    	{
    		this.name = name; 
    		modified = new Date();
    	}
	}

    @NotNull
    private Long author;
    public Long getAuthor() { return author; }
    public void setAuthor(Long author) { this.author = author; }
    
    @Basic
    @Past
    private Date created = new Date();
    public Date getCreated() { return created; }
   
    @Basic
    @Past
    protected Date modified = new Date();
    public Date getModified() { return modified; }

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
    		Material attached = em.find(Material.class, this.id);
    		em.remove(attached); 
    	}
    	finally { em.close(); }
    }

    public static Material findMaterial(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		Material m = em.find(Material.class, id);
    		return m;
    	}
    	finally { em.close(); }
    }
}
