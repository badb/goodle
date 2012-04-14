package edu.goodle.prototype.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.google.appengine.api.datastore.Key;

@NamedQuery
(
		name = "findSessionsByUser",
		query = "SELECT s FROM GoodleSession s WHERE s.user = :user"
)
@Entity
public class GoodleSession implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    public Key getKey() { return key; }
    
    @OneToOne
    private Key user;
    public Key getUser() { return user; }

    public GoodleSession(GoodleUser user) { this.user = user.getKey(); }

}