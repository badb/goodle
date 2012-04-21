package main.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

@Entity
public class Module implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private boolean isVisible;
    public boolean getIsVisible() { return isVisible; }
    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    
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

}
