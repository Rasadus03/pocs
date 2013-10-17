package org.jboss.test.messaging;

import java.util.Map;
import java.util.HashMap;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

import javax.jms.ConnectionFactory;

public class HornetqAPILoadTest extends JMSLoadTest implements IJMSLoadTest {


    // this implementation uses proprietary Hornetq mechanism to instantiate JMS Connection and Destination w/out using JNDI
    public void setConnectionAndDestination() throws javax.jms.JMSException {
        Map<String, Object> connectionParams = new HashMap<String, Object>();
        connectionParams.put(TransportConstants.PORT_PROP_NAME, brokerPort);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, brokerAddress);
        TransportConfiguration tConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName(), connectionParams);
        ConnectionFactory cFactory = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, tConfiguration);
        connection = cFactory.createConnection();

        bulkMessageDestination = HornetQJMSClient.createQueue(bulkMessageDestinationName);
        controlDestination = HornetQJMSClient.createQueue(controlDestinationName);
    }
}
