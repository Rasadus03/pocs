package org.jboss.as7.lab;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import javax.persistence.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class IdentityManagement implements IIdentityManagement {

    private Logger log = LoggerFactory.getLogger("IdentityManagement");
    private @PersistenceUnit(unitName="idmgt-EMF") EntityManagerFactory idmgtEMF;


    @PostConstruct
    public void start() throws Exception {
        log.info("start()");
        executeIdMgtUserAndGroupDataLoad();
    }


    private void executeIdMgtUserAndGroupDataLoad() throws Exception {
        try {
            log.info("executeIdMgtUserAndGroupDataLoad() ");
            EntityManager eManager = idmgtEMF.createEntityManager();
            eManager.persist(new IdMgtUser("owner", "owner"));
            eManager.persist(new IdMgtUser("manager", "manager"));
            eManager.persist(new IdMgtUser("finance", "finance"));
            eManager.persist(new IdMgtUser("guest", "guest"));

            eManager.persist(new IdMgtRole(IIdentityManagement.OWNER, "owner"));
            eManager.persist(new IdMgtRole(IIdentityManagement.OWNER, "manager"));
            eManager.persist(new IdMgtRole(IIdentityManagement.OWNER, "finance"));

            eManager.persist(new IdMgtRole(IIdentityManagement.MANAGER, "manager"));
            eManager.persist(new IdMgtRole(IIdentityManagement.FINANCE, "finance"));
            eManager.persist(new IdMgtRole(IIdentityManagement.GUEST, "guest"));
        } finally {
        }
    }
    


    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
