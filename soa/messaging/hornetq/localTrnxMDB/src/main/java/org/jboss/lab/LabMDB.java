package org.jboss.lab;

import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@MessageDriven(name = "LabMDB", activationConfig = { 
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/org.jboss.controlQueue"),
    @ActivationConfigProperty(propertyName = "useLocalTx", propertyValue = "true")      // specify use of a Hornetq local (non-jta) trnx
})
@TransactionManagement(value = TransactionManagementType.CONTAINER)

// this MDB does not support a JTA trnx
// setting this to REQUIRED would prevent trnx boundaries on the Hornetq local trnx to be set ... message would subsequently not be committed
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class LabMDB implements MessageListener {

    private @Resource MessageDrivenContext ctx;
    private @PersistenceUnit(unitName="xa_EMF")  EntityManagerFactory xaEMF;
    private @PersistenceUnit(unitName="local_EMF")  EntityManagerFactory localEMF;
    private @Resource(name="java:/TransactionManager") TransactionManager tMgr;

    private Logger log = LoggerFactory.getLogger("LabMDB");
    private Random rGenerator = new Random();

    public void onMessage(final Message message) {
        EntityManager localEM = null;
        try {
            Transaction tx = tMgr.getTransaction();
            if(tx != null)
                throw new RuntimeException("onMessage() uh-oh .. detected active container managed transaction when expecting Hornetq local trnx");

            String textBody = ((TextMessage)message).getText();
            String pojoId = textBody+"_"+rGenerator.nextInt(); 
            log.info("onMessage() received message with body = "+pojoId);
            Pojo pojo = new Pojo();
            pojo.setId(pojoId);

            EntityManager emObj = xaEMF.createEntityManager();

            // check "test" database ... notice that because an XA trnx has not been created .... this will not flush
            emObj.persist(pojo);

            localEM = localEMF.createEntityManager();
            EntityTransaction eTrnx = localEM.getTransaction();
            eTrnx.begin();
            localEM.persist(pojo);
            eTrnx.commit();

        } catch(RuntimeException x) {
            throw x;
        } catch(Exception x) {
            ctx.setRollbackOnly();
        } finally {
            if(localEM != null) {
                localEM.close(); // bean is responsible for closing RESOURCE_LOCAL EMF
            }
        }
    }
}
