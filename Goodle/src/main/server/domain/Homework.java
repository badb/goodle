package main.server.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;

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
    
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<UploadedFile> solutions = new ArrayList<UploadedFile>();
    public List<UploadedFile> getSolutions() { return Collections.unmodifiableList(solutions);  }
    public void setMaterials(List<UploadedFile> solutions) { 
    	EntityManager em = entityManager();
    	BlobstoreService blobstore = BlobstoreServiceFactory.getBlobstoreService();
    	try {
    		for (UploadedFile uf : this.solutions) {
    			if (!solutions.contains(uf)) {
    				UploadedFile u = em.find(UploadedFile.class, uf.getId());
    				blobstore.delete(uf.getKey());
    				em.remove(u);
    			}
    		}
    		this.solutions = solutions;
    	}
    	finally {em.close();}
    	
    }
    public void addSolution(UploadedFile solution) { solutions.add(solution); }
    public void removeSolution(UploadedFile solution) { solutions.remove(solution); }
  
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
