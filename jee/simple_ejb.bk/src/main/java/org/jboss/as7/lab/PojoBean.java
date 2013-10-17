package org.jboss.as7.lab;

import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.transaction.UserTransaction;

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
    private @javax.annotation.Resource UserTransaction uTrnx;

    @PostConstruct
    public void start() {
        log.info("start()  uTrnx = "+uTrnx);
    }

    /** 
     * assumes client sets pojoId
     * assumes that container is going to introduce a JTA trnx and control the trnx boundaries
     */
    public Pojo createPojoCMT(Pojo pojo) {
        log.info("createPojoViaJTAEntityManager() id = "+pojo.getId());
        return pojo;
    }

   
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED) 
    public Pojo createPojoBMT(Pojo pojo) {
        try {
            uTrnx.begin();
            uTrnx.commit();
            log.info("createPojoViaResourceLocalEntityManager() id = "+pojo.getId());
            return pojo;
        } catch(Exception x) {
            x.printStackTrace();
            throw new RuntimeException(x);
        } finally {
        }
    }

    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
