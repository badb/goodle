package edu.goodle.prototype.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class GoodleSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    public Key getKey() { return key; }
    
    @OneToOne
    private Key user;
    public Key getUser() { return user; }

    public GoodleSession(GoodleUser user) { this.user = user.getKey(); }

}
