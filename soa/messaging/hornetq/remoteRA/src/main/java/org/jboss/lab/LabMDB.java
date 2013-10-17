package org.jboss.lab;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.*;

import org.apache.log4j.Logger;

@MessageDriven(name = "LabMDB", activationConfig = { 
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "target = 'mdb'"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/org.jboss.labQueue")
})
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class LabMDB implements MessageListener {

    private static Connection connectObj;
    private static Object lockObj = new Object();

    private @Resource MessageDrivenContext ctx;
    private @Resource(name="java:/RemoteConnectionFactory") ConnectionFactory cFactory;

    private Logger log = Logger.getLogger("LabMDB");

    @PostConstruct
    void init() throws JMSException{
        if(connectObj == null) {
            synchronized(lockObj) {
                if(connectObj != null)
                    return;

                connectObj = cFactory.createConnection();
                connectObj.setExceptionListener(new ExceptionListener() {
                    public void onException(final JMSException e) {
                        log.error("JMSException = "+e.getLocalizedMessage());
                    }
                });

                log.info("init()  connectObj = "+connectObj);
            }
        }
    }

    @PreDestroy
    void close() throws Exception {
        log.info("close()");
    }

    public void onMessage(final Message message) {
        Session producerSession = null;
        try {
            String textBody = ((TextMessage)message).getText();
            log.info("onMessage() received message with body = "+textBody);
            if(message.getJMSReplyTo() != null){
                producerSession = connectObj.createSession(false, Session.AUTO_ACKNOWLEDGE);
                TextMessage mResponse = producerSession.createTextMessage();
                mResponse.setText(textBody);
                mResponse.setJMSCorrelationID(message.getJMSCorrelationID());
                MessageProducer producer = producerSession.createProducer(null);
                producer.send(message.getJMSReplyTo(), mResponse);
            }
        } catch(RuntimeException x) {
            throw x;
        } catch(Exception x) {
            x.printStackTrace();
        } finally {
            if(producerSession != null) {
                try { producerSession.close();} catch(Exception x) {x.printStackTrace();}
            }
        }
    }
}
