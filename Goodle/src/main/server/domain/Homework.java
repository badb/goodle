package main.server.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Homework extends Material
{
	@NotNull
    private String text;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
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
    
    @OneToMany(cascade=CascadeType.ALL)
    private List<Long> solutions = new ArrayList<Long>();
    public List<Long> getSolutions() { return Collections.unmodifiableList(solutions); }
    public void addSolution(HomeworkFile file) { solutions.add(file.getId()); }
    public void removeSolution(HomeworkFile file) { solutions.remove(file.getId()); }
    
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
