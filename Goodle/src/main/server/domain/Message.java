package main.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.google.appengine.api.datastore.Key;


@SuppressWarnings("serial")
@Entity
public class Message implements Serializable
{	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	public Key getId() { return id; }
	
    /*@NotNull
    private GoodleUser author;
    public GoodleUser getAuthor() { return author; }
    public void setAuthor(GoodleUser author) { this.author = author; }
    */
    @NotNull
    private String text;
    public String getText() { return text; }
    public void setText(String text)
    {
    	this.text = text;
    	modified = new Date();
    }

    //@Basic
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "comments", referencedColumnName = "module_id")
	private Module module;
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	

    @Basic
    @Past
    private Date created = new Date();
    public Date getCreated() { return created; }

    @Basic
    @Past
    private Date modified = new Date();
    public Date getModified() { return modified; }
    
}
