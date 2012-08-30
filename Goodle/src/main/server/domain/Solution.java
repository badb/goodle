package main.server.domain;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;


@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("SOLUTION")
public class Solution extends UploadedFile {
	
	@Basic
	private String comment = "";
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Basic
	private Float mark;
	public Float getMark() {
		return mark;
	}
	public void setMark(Float mark) {
		this.mark = mark;
	}
	
	@Basic 
	private Boolean checked = false;	
	public Boolean isChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	
	
    public static Solution findSolution(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		Solution m = em.find(Solution.class, id);
    		return m;
    	}
    	finally { em.close(); }
    }

}
