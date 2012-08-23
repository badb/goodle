package main.server.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@SuppressWarnings("serial")
@Entity
public class Homework extends Module
{
    @Basic
    @Past
    protected Date modified = new Date();
    public Date getModified() { return modified; }
    
    @Basic
    private Date deadline;
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) 
    { 
    	if (this.deadline != deadline)
    	{
    		this.deadline = deadline; 
    		modified = new Date();
    	}
    }
        
    private boolean isVisible;
    public boolean getIsVisible() { return isVisible; }
    public void setIsVisible(boolean isVisible) { this.isVisible = isVisible; }
    
    public static Homework findHomework(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		Homework h = em.find(Homework.class, id);
    		return h;
    	}
    	finally { em.close(); }
    }
}

