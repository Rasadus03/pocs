package org.jboss.gpse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojoBean{

    private Logger log = LoggerFactory.getLogger("PojoBean");

    public Pojo createPojo(Pojo pojo) {
        return pojo;
    }
}
