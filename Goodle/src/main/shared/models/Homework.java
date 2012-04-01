package main.shared.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
public class Homework extends Material 
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Key key;
    public Key getKey() { return key; }
    
    private Text desc;
    public Text getDesc() { return desc; }
    public void setDesc(Text desc) { this.desc = desc; }
    
    private Date deadline;
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<HomeworkFile> provided = new ArrayList<HomeworkFile>();
    public List<HomeworkFile> getProvided() { return Collections.unmodifiableList(provided); }
    public void addProvided(HomeworkFile file) { provided.add(file); }
    public void removeProvided(HomeworkFile file) { provided.remove(file); }
    
    public Homework() { }
    
    public Homework(GoodleUser author, String name, Text desc, Date deadline)
    {
    	super(name, author);
    	this.desc = desc;
    	this.deadline = deadline;
    }

}
