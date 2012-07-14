package main.server.domain;

import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class UploadedFile extends Material 
{
	@NotBlank
	@URL
	private String url;
	public String getUrl() { return url; }
	public void setUrl(String url) 
	{ 
		if (this.url != url)
		{
			this.url = url;
			modified = new Date();
		}
	}
	
    public static UploadedFile findUploadedFile(Long id) 
    {
    	if (id == null) { return null; }
    	EntityManager em = entityManager();
    	try 
    	{
    		UploadedFile f = em.find(UploadedFile.class, id);
    		return f;
    	}
    	finally { em.close(); }
    }
}
