package main.server.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("HOMEWORK")
public class Homework extends Module
{

    @Basic
    private Date deadline;
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date date) { deadline = date; } 
    
    private Long course;
    public Long getCourse() { return course; }
    public void setCourse(Long course) { this.course = course; }
    
    @Basic
    private List<Long> solutionsIds = new ArrayList<Long>();
    public List<Long> getSolutionsIds() { return Collections.unmodifiableList(solutionsIds); }
    public void addSolutionId(Long val) { solutionsIds.add(val); }
    public void removeSolutionId(Long val) { solutionsIds.remove(val); }
    
    @Transient
    private List<UploadedFile> solutions;
    public List<UploadedFile> getSolutions() { return solutions; }
    public void setSolutions(List<UploadedFile> files) { solutions = new ArrayList<UploadedFile>(files); }
    
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

