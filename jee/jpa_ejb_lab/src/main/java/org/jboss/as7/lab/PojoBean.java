package org.jboss.as7.lab;

import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.Context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Remote(IPojoBean.class)
public class PojoBean implements IPojoBean {

    private Logger log = LoggerFactory.getLogger("PojoBean");

    private @PersistenceUnit(unitName="lab_EMF")  EntityManagerFactory labEMF;
    private @PersistenceUnit(unitName="lab_EMF_Resource_Local")  EntityManagerFactory labEMFResourceLocal;

    @PostConstruct
    public void start() {
        StringBuilder sBuilder = new StringBuilder("start() resource local enabled EMF props as follows: ");
        Map<String, Object> props = labEMFResourceLocal.getProperties();
        String hString = "hibernate";
        String pString = "persistence";
        String jString = "jndi";
        for (Map.Entry<String, Object> entry: props.entrySet()) {
            String keyValue = entry.getKey();
            if(keyValue.indexOf(hString) > -1 || keyValue.indexOf(pString) > -1 || keyValue.indexOf(jString) > -1) {
                sBuilder.append("\n\tkey ="+entry.getKey()+"\t: value = "+entry.getValue());
            }
        }
        log.info(sBuilder.toString());
    }

    /** 
     * assumes client sets pojoId
     * assumes that container is going to introduce a JTA trnx and control the trnx boundaries
     */
    public Pojo createPojoViaJTAEntityManager(Pojo pojo) {
        EntityManager emObj = labEMF.createEntityManager();
        emObj.persist(pojo);
        log.info("createPojoViaJTAEntityManager() id = "+pojo.getId());
        try{
            Thread.sleep(30000);
        }catch(Exception x) { x.printStackTrace();  }
        return pojo;
    }

   
    /** 
     * assumes client sets pojoId
     * notice the use of Bean Managed Trnx Demarcation by specifying:  TransactionAttributeType.NOT_SUPPORTED
     *      this setting suspends the containers JTA trnx ... which we can not use becuase our EMF is configured for RESOURCE_LOCAL
     * we still need to specify trnx boundaries somehow to tell the database when to begin and commit
     * since we are using a RESOURCE_LOCAL EMF, we subsequently use the non-JTA trnx provided by the EntityManager to define those trnx boundaries
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
    public Pojo createPojoViaResourceLocalEntityManager(Pojo pojo) {
        EntityManager emObj = null;
        try {
            emObj = labEMFResourceLocal.createEntityManager();
            EntityTransaction eTrnx = emObj.getTransaction();
            eTrnx.begin();
            emObj.persist(pojo);
            eTrnx.commit();
            log.info("createPojoViaResourceLocalEntityManager() id = "+pojo.getId());
            return pojo;
        } finally {
            if(emObj != null) 
                emObj.close();
        }
    }

    
    public void updatePojo(Pojo pojoObj) {
        EntityManager emObj = labEMF.createEntityManager();
        emObj.merge(pojoObj);
    }

    public void removePojo(String pojoId) {
        EntityManager emObj = labEMF.createEntityManager();
        Pojo pojoObj = emObj.find(Pojo.class, pojoId);
        emObj.remove(pojoObj);
    }

    public Pojo getPojo(String id) {
        EntityManager emObj = labEMF.createEntityManager();
        return emObj.find(Pojo.class, id);
    }

    public List<Pojo> listPojos() {
        EntityManager emObj = labEMF.createEntityManager();
        List<Pojo> pojos = emObj.createQuery("from Pojo").getResultList();
        return pojos;
    }

    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
