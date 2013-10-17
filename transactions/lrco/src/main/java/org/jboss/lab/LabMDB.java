package org.jboss.lab;

import java.util.Random;

import javax.annotation.PostConstruct;
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
import javax.transaction.UserTransaction;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// this MDB will be the JTA aware resource in this scenario
@MessageDriven(name = "LabMDB", activationConfig = { 
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/org.jboss.controlQueue"),
})

/* will use bean managed transactions for this lab
 * reason is that with CMT, the arjuna TM throws its javax.transaction.RollbackException outside the scope of the onMessage() method
 * thus preventing the ability to catch and inspect the exception in this bean
 */
@TransactionManagement(value = TransactionManagementType.BEAN)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class LabMDB implements MessageListener {

    private @Resource MessageDrivenContext ctx;
    private @Resource(name="java:/TransactionManager") TransactionManager tMgr;
    private @Resource UserTransaction uTrnx;

    // this EMF will be the first XA-aware EMF using a non-XA-aware data source
    private @PersistenceUnit(unitName="xa_EMF")  EntityManagerFactory xa_EMF;

    // this EMF will be the second XA-aware EMF using a non-XA-aware data source
    private @PersistenceUnit(unitName="xa_two_EMF")  EntityManagerFactory xa_two_EMF;

    private Logger log = LoggerFactory.getLogger("LabMDB");
    private Random rGenerator = new Random();
    private boolean enlistSecondNonJtaResource = true;

    @PostConstruct
    void init() {
        if(System.getProperty("enlistSecondNonJtaResource") != null)
            enlistSecondNonJtaResource = Boolean.parseBoolean(System.getProperty("enlistSecondNonJtaResource"));
    }

    public void onMessage(final Message message) {
        try {
            Transaction tx = tMgr.getTransaction();
            if(tx != null)
                throw new RuntimeException("onMessage() not expecting a container managed transaction");

            String textBody = ((TextMessage)message).getText();
            String pojoId = textBody+"_"+rGenerator.nextInt(); 

            // let's not continue to throw the same exception
            if(message.getJMSRedelivered()) {
                log.info("onMessage() message received is redelivered.  textBody = "+textBody);
                return;
            }

            log.info("onMessage() received message with body = "+pojoId);
            Pojo pojo = new Pojo();
            pojo.setId(pojoId);

            uTrnx.begin();
            EntityManager xaEM = xa_EMF.createEntityManager();
            xaEM.persist(pojo);

            if(enlistSecondNonJtaResource) {
                EntityManager xaTwoEM = xa_two_EMF.createEntityManager();
                xaTwoEM.persist(pojo);
            }

            uTrnx.commit();
        } catch(Throwable x) {
            log.error("onMessage() ******* throwable = "+x.getClass().toString());
            ctx.setRollbackOnly();
        }
    }
}
