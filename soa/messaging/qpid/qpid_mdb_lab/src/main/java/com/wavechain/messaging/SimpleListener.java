package com.wavechain.messaging;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleListener implements MessageListener {

    public static final String TRIGGER_EXCEPTION = "TRIGGER_EXCEPTION";
    public static final String SLEEP_TIME = "SLEEP_TIME";

    private static Logger log = LoggerFactory.getLogger("SimpleListener");
    private static int postConstructCount = 1;

    private boolean triggerException = true;
    private int sleepTime = 500;

    public SimpleListener() {
        try {
            String triggerExceptionString = System.getProperty(TRIGGER_EXCEPTION);
            if(triggerExceptionString != null)
                triggerException = Boolean.valueOf(triggerExceptionString);

            String sleepTimeString = System.getProperty(SLEEP_TIME);
            if(sleepTimeString != null)
                sleepTime = Integer.parseInt(sleepTimeString);

        }catch(Exception x) {
            x.printStackTrace();
        }
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct() instance # = "+postConstructCount);
        postConstructCount++;
    }

    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage) {

                ObjectMessage oMessage = (ObjectMessage)message;
                   log.info("onMessage() messageId = "+oMessage.getJMSMessageID()+" : redelivered = "+oMessage.getJMSRedelivered()+" : object = "+oMessage.getObject());
                Thread.sleep(sleepTime);
                if(triggerException)
                    throw new RuntimeException("onMessage() triggering exception during processing of messageId = "+oMessage.getJMSMessageID());

           } else if (message instanceof TextMessage) {

                TextMessage tMessage = (TextMessage)message;
                log.info("onMessage() this MDB = "+this+" : messageId = "+tMessage.getJMSMessageID()+" :  redelivered = "+tMessage.getJMSRedelivered()+" : text = "+tMessage.getText());
                Thread.sleep(sleepTime);
                if(triggerException)
                        throw new RuntimeException("onMessage() triggering exception during processing of messageId = "+tMessage.getJMSMessageID());

           } else {
                log.error("message = "+message.getClass());
           }
        } catch(RuntimeException x) {
            throw x;
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
