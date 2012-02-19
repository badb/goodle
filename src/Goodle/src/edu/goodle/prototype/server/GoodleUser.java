package edu.goodle.prototype.server;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.google.appengine.api.datastore.Key;

@Entity
public class GoodleUser implements Serializable {

        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Key key;
        private String login; //tymczasowo
        private String password; //tymczasowo
        private String access_token_key;
        private String access_token_secret;
        private String request_key;

        public Key getKey() {
                return key;
        }

        public String getLogin() {
                return login;
        }
        public void setLogin(String login) {
                this.login = login;
        }
        public String getPassword() {
                return password;
        }
        public void setPassword(String password) {
                this.password = password;
        }
        public String getAccess_token_key() {
                return access_token_key;
        }
        public void setAccess_token_key(String accessTokenKey) {
                access_token_key = accessTokenKey;
        }
        public String getAccess_token_secret() {
                return access_token_secret;
        }
        public void setAccess_token_secret(String accessTokenSecret) {
                access_token_secret = accessTokenSecret;
        }
        public String getRequest_key() {
                return request_key;
        }
        public void setRequest_key(String requestKey) {
                request_key = requestKey;
        } 


}
