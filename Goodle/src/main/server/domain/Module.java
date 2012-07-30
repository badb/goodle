package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@SuppressWarnings("serial")
public class Module implements Serializable 
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
    

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<UploadedFile> materials = new ArrayList<UploadedFile>();
    public List<UploadedFile> getMaterials() { return materials; }
    public void setMaterials(List<UploadedFile> materials) { this.materials = materials; }
    public void addMaterial(UploadedFile material) { materials.add(material); }
    public void removeMaterial(UploadedFile material) { materials.remove(material); }

    @OneToMany(cascade=CascadeType.ALL)
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }

    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    public void persist() 
    {
    	EntityManager em = entityManager();
    	try { em.persist(this); }
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
