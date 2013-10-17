package org.jboss.test.messaging;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.jms.*;

public class JMSAPILoadTest extends JMSLoadTest implements IJMSLoadTest {

    public void setConnectionAndDestination() throws javax.jms.JMSException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, namingProviderUrl);
        jndiProps.put(Context.SECURITY_PRINCIPAL, "guest");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "guestguest");
        Context jmsProviderContext = null;
        try {
            jmsProviderContext = new InitialContext(jndiProps);

            ConnectionFactory cFactory = (ConnectionFactory)jmsProviderContext.lookup(connectionFactory);
            connection = cFactory.createConnection();

            bulkMessageDestination = (Destination)jmsProviderContext.lookup(bulkMessageDestinationPath);
            controlDestination = (Destination)jmsProviderContext.lookup(controlDestinationPath);
        } catch(Exception x) {
            throw new RuntimeException(x);
        } finally {
            if(jmsProviderContext != null) {
                try {
                    jmsProviderContext.close();
                } catch(Exception x) { x.printStackTrace(); }
            }
        }
    }
}
