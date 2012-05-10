package main.server.domain;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@SuppressWarnings("serial")
@Entity
public class HomeworkFile extends UploadedFile 
{
    private Mark mark;
    public Mark getMark() { return mark; }
    public void setMark(Mark mark) { this.mark = mark; }
    
    public static HomeworkFile findHomeworkFile(Long id)
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		HomeworkFile f = em.find(HomeworkFile.class, id);
    		return f;
    	}
    	finally { em.close(); }
    }
}
