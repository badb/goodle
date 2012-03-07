package edu.goodle.prototype.db;

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
public class Module {
	
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
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return Collections.unmodifiableList(comments); }
    public void addComment(Message comment) { comments.add(comment); }
    public void removeComment(Message comment) { comments.remove(comment); }
    
    public Module() { materials = new ArrayList<Material>(); }
    public Module(Collection<Material> materials) { this.materials = new ArrayList<Material>(materials); }  
    public Module(Module m) { materials = new ArrayList<Material>(m.getMaterials()); }

}
