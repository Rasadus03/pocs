package org.jboss.gpse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojoBean{

    private Logger log = LoggerFactory.getLogger("PojoBean");

    private @PersistenceUnit(unitName="jta_EMF")  EntityManagerFactory jtaEMF;
    private @PersistenceUnit(unitName="resource_local_EMF")  EntityManagerFactory resourceLocalEMF;


    // assumes client sets pojoId
    @Transactional
    public Pojo createPojo(Pojo pojo) {
        EntityManager emObj = jtaEMF.createEntityManager();
        emObj.persist(pojo);
        return pojo;
    }
}
