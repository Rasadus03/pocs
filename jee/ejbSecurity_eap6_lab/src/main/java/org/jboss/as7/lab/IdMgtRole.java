package org.jboss.as7.lab;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class IdMgtRole {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String roleId;
    private String userId;

    public IdMgtRole(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getId() { return id;  }

    public String getRoleId() { return roleId;  }
    public void setRoleId(String x) {   this.roleId = x;    }
    public String getUserId() { return userId;  }
    public void setUserId(String x) {   this.userId = x;    }
}
