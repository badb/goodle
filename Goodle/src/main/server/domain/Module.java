package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

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
	
    private String title;	
    public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title; }
	
    private String desc;	
    public String getDesc() {return desc;	}
	public void setDesc(String desc) {this.desc = desc; }
    
    private boolean isVisible;
    public boolean getIsVisible() { return isVisible; }
    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Material> materials = new ArrayList<Material>();
    public List<Material> getMaterials() { return Collections.unmodifiableList(materials); }
    public void addMaterial(Material material) { materials.add(material); }
    public void removeMaterial(Material material) { materials.remove(material); }

    @OneToMany(cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();
    public List<Comment> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Comment comment) { comments.add(comment); }
    public void removeComment(Comment comment) { comments.remove(comment); }

    public static final EntityManager entityManager() { return EMF.get().createEntityManager(); }
    
    public void persist() 
    {
    	EntityManager em = entityManager();
    	try { em.persist(this); }
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
    
    public static List<Module> findModules(List<Long> ids)
    {
    	EntityManager em = entityManager();
    	List<Module> result = new ArrayList<Module>();
    	try
    	{
    		for (Long id : ids)
    		{
    			Module m = findModule(id);
    			if (m != null) result.add(m);
    		}
    		return result;
    	}
    	finally { em.close(); }
    }

}
