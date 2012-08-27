package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuppressWarnings("serial")
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
    
    @Basic //TODO ?
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="module")
    private List<UploadedFile> materials = new ArrayList<UploadedFile>();
    public List<UploadedFile> getMaterials() { return Collections.unmodifiableList(materials);  }
    public void setMaterials(List<UploadedFile> materials) { 
    	EntityManager em = entityManager();
    	BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
    	try {
    		for (UploadedFile uf : this.materials) {
    			if (!materials.contains(uf)) {
    				UploadedFile u = em.find(UploadedFile.class, uf.getId());
    				blobstore.delete(uf.getKey());
    				em.remove(u);
    			}
    		}
    		this.materials = materials;
    	}
    	finally {em.close();}
    	
    }
    public void addMaterial(UploadedFile material) { materials.add(material); }
    public void removeMaterial(UploadedFile material) { materials.remove(material); }

    /*@Basic
    private List<Long> materials = new ArrayList<Long>();
    public List<Long> getMaterials() { return Collections.unmodifiableList(materials);  }
    public void setMaterials(List<Long> materials) { this.materials = materials; }
    public void addMaterial(Long material) { materials.add(material); }
    public void removeMaterial(Long material) { materials.remove(material); }
    public List<UploadedFile> getMaterialProxies() {
    	EntityManager em = entityManager();
    	List<UploadedFile> l = new ArrayList<UploadedFile>();
    	try
    	{
    		for (Long id : materials)
    		{
    			UploadedFile m = em.find(UploadedFile.class, id);
    			l.add(m);
    		}
    		return l;
    	}
    	finally { em.close(); }
    }
    public Module setMaterialProxies(List<UploadedFile> materials) {
    	EntityManager em = entityManager();

		List<Long> newMaterials = new ArrayList<Long>();
    	try
    	{
    		Module m = em.find(Module.class, this.id);
    		for (UploadedFile uf : materials)
    		{
    			em.persist(uf);
    			em.refresh(uf);
    			newMaterials.add(uf.getId());
    		}
    		for (Long id : m.materials)
    		{
    			UploadedFile uf = em.find(UploadedFile.class, id);
    			em.remove(uf);
    		}
    		m.materials = newMaterials;
    		em.persist(m);
    		return m;
    	}
    	finally { em.close(); }
    }
    */
    
    @Basic
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="module")
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }
    public void setComments(List<Message> comments) { this.comments = comments; }

    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    public void persist() 
    {
    	EntityManager em = entityManager();
    	try { 
    		//for (UploadedFile uf: materials)
    		em.persist(this); 
    		em.refresh(this);//?
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
