package main.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;


@SuppressWarnings("serial")
public class Message implements Serializable
{	
    @NotNull
    private GoodleUser author;
    public GoodleUser getAuthor() { return author; }
    public void setAuthor(GoodleUser author) { this.author = author; }
   
    @NotNull
    private String text;
    public String getText() { return text; }
    public void setText(String text) 
    {
    	this.text = text;
    	modified = new Date();
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
