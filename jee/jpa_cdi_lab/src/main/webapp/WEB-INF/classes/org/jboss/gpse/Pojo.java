package org.jboss.gpse;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pojo implements Serializable {

    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }
    public void setId(String pojoId) {
        id = pojoId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
