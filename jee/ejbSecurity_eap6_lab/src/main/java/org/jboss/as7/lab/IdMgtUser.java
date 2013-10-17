package org.jboss.as7.lab;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IdMgtUser {

    @Id
    private String id;
    private String password;

    public IdMgtUser(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() { return id;  }
    public void setId(String x) {   this.id = x;    }
    public String getPassword() { return password;  }
    public void setPassword(String x) {   this.password = x;    }
}
