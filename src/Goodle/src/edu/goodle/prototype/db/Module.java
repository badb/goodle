package edu.google.prototype.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Module {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private List<Material> materials;
    public List<Material> getMaterials() { return materials; }
    
    private List<Message> comments = new ArrayList<Message>();
    public List<Message> getComments() { return comments; }
    
    public Module() { materials = new ArrayList<Material>(); }
    public Module(Collection<Material> materials) { this.materials = new ArrayList<Material>(materials); }  
    public Module(Module m) { materials = new ArrayList<Material>(m.getMaterials()); }

}
