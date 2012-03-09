package edu.goodle.prototype.server;

import java.io.Serializable;

import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class GoodleSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Key user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Key getUser() {
            return user;
    }

    public void setUser(Key user) {
            this.user = user;
    } 

}
