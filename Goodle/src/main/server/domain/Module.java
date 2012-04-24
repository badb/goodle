package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.google.appengine.api.datastore.Key;

import main.server.domain.EMF;


@Entity
public class Module implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key id;
    public Key getId() { return id; }
    
    @Version
    @Column(name="version")
    private Integer version;
	public Integer getVersion() { return version;	}
	public void setVersion(Integer version) { this.version = version;	}
    
    
    private boolean isVisible;
    public boolean getIsVisible() { return isVisible; }
    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    
    private String description;	public String getDescription() {return description;	}
	public void setDescription(String description) {this.description = description;	}
    
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Material> materials;
    public List<Material> getMaterials() { return Collections.unmodifiableList(materials); }
    public void addMaterial(Material material) { materials.add(material); }
    public void removeMaterial(Material material) { materials.remove(material); }

    @OneToMany(cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();
    public List<Comment> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Comment comment) { comments.add(comment); }
    public void removeComment(Comment comment) { comments.remove(comment); }
    
    public Module() { materials = new ArrayList<Material>(); }
    
    public Module(Collection<Material> materials, boolean isVisible) 
    { 
    	this.materials = new ArrayList<Material>(materials); 
    	this.isVisible = isVisible;
    }  
    
    public Module(Module m, boolean isVisible) 
    { 
    	materials = new ArrayList<Material>(m.getMaterials()); 
    	this.isVisible = isVisible;
    }


    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    public void persist() 
    {
    	EntityManager em = entityManager();
    	try { em.persist(this); }
    	finally { em.close(); }
    }
    
    public static Module findModule(Key id) {
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
