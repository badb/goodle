package edu.mimuw.goodle.server;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class GoodleSession implements Serializable {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private GoodleUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GoodleUser getUser() {
		return user;
	}

	public void setUser(GoodleUser user) {
		this.user = user;
	} 

	
}
