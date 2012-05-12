package main.server.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Mark implements Serializable
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
    public Long getId() { return id; }
    
    private int val;
    public int getVal() { return val; }
    public void setVal(int val) { this.val = val; }
    
    @NotNull
    private GoodleUser teacher;
    public GoodleUser getTeacher() { return teacher; }
    public void setTeacher(GoodleUser teacher) { this.teacher = teacher; }
    
    @NotNull
    private String comment;
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
