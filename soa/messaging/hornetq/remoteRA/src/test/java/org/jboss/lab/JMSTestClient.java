package org.jboss.lab;

import java.io.File;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.InitialContext;

import javax.jms.*;
import javax.naming.Context;

import org.apache.log4j.Logger;

import org.hornetq.api.core.DiscoveryGroupConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;

/**
 *  JMSTestClient
 *  JA Bride
 *  23 March, 2008
 */
public class JMSTestClient {

    public static final String TIMED_OUT_WAITING_FOR_RESPONSE="Timed out waiting for response when sending packet";
    public static final String PROPERTIES_FILE_NAME="/lab.properties";
    private static Logger log = Logger.getLogger(JMSTestClient.class);

    private static String cFactoryName = "jms/RemoteConnectionFactory";
    private static int clientCount = 0;
    private static int requestsPerClient = 0;
    private static int byteMessageSize = 1;
    private static boolean isPersistent = false;
    private static String gwDObjName = null;
    private static String gwDObjPath = null;
    private static int sleepTimeMillis = 0;
    private static int totalCount = 0;
    private static Connection connectionObj = null;
    private static Destination gwDObj = null;
    private static ExecutorService execObj = null;
    private static BigDecimal bdThousand;
    private static boolean enableLog = true;
    private static int discoverPort = 0;
    private static String discoverHost;
    private static int labPort = 0;
    private static String labHost;
    private static boolean useDiscover=true;
    private static String mSelector="mdb";
    private static int receiveBlockTimeMillis=30000;
    private static boolean setDupsStringProperty=true;
    private static final ConcurrentMap<String, AtomicInteger> serverNodeCountHash = new ConcurrentHashMap<String, AtomicInteger>();

    public static void main(String args[]) {
        Properties properties = new Properties();
        try {
            properties.load(JMSTestClient.class.getResourceAsStream(PROPERTIES_FILE_NAME));
            if(properties.size() == 0)
                throw new RuntimeException("start() no properties defined in "+PROPERTIES_FILE_NAME);

            clientCount = Integer.parseInt(properties.getProperty("org.jboss.lab.clientCount", "1"));
            requestsPerClient = Integer.parseInt(properties.getProperty("org.jboss.lab.requestsPerClient", "1"));

            mSelector=properties.getProperty("org.jboss.lab.message.selector", "mdb");
            receiveBlockTimeMillis = Integer.parseInt(properties.getProperty("org.jboss.lab.receive.block.time.millis", "30000"));

            enableLog = Boolean.parseBoolean(properties.getProperty("org.jboss.lab.enableLog"));
            useDiscover = Boolean.parseBoolean(properties.getProperty("org.jboss.lab.test.useDiscover", "true"));
            setDupsStringProperty = Boolean.parseBoolean(properties.getProperty("org.jboss.lab.test.setDupsStringProperty", "true"));

            sleepTimeMillis = Integer.parseInt(properties.getProperty("org.jboss.lab.sleepTimeMillis", "100"));

            discoverHost = properties.getProperty("jboss.messaging.group.address", "localhost");
            discoverPort = Integer.parseInt(properties.getProperty("jboss.messaging.group.port", "4547"));
            labHost = properties.getProperty("remote.connection.lab.host", "localhost");
            labPort = Integer.parseInt(properties.getProperty("remote.connection.lab.port", "4547"));

            isPersistent = Boolean.parseBoolean(properties.getProperty("org.jboss.lab.isPersistent", "TRUE"));
            gwDObjName = properties.getProperty("org.jboss.destinationName");
            gwDObjPath = properties.getProperty("org.jboss.destinationPath");


            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("\n\tclientCount = "+clientCount);
            sBuilder.append("\n\tmessagesPerClient = "+requestsPerClient);
            sBuilder.append("\n\tisPersistent = "+isPersistent);
            sBuilder.append("\n\tgateway destination = "+gwDObjName);
            sBuilder.append("\n\tsleepTime = "+sleepTimeMillis);
            sBuilder.append("\n\tdiscoverHost = "+discoverHost);
            sBuilder.append("\n\tdiscoverPort = "+discoverPort);
            sBuilder.append("\n\tuseDiscover = "+useDiscover);
            sBuilder.append("\n\tmSelector = "+mSelector);
            sBuilder.append("\n\tsetDupsStringProperty = "+setDupsStringProperty);
            sBuilder.append("\n\treceiveBlockTimeMillis = "+receiveBlockTimeMillis);
            log.info(sBuilder.toString());

            ConnectionFactory cFactory = null;

            if(useDiscover) {
                // use proprietary hornetq classes to discover brokers on the network

                // upgrade to use latest hornetq API 
                //UDPBroadcastGroupConfiguration udpCfg = new UDPBroadcastGroupConfiguration("231.7.7.7", 9876, null, -1);
                //DiscoveryGroupConfiguration groupConfiguration = new DiscoveryGroupConfiguration(HornetQClient.DEFAULT_DISCOVERY_INITIAL_WAIT_TIMEOUT, HornetQClient.DEFAULT_DISCOVERY_INITIAL_WAIT_TIMEOUT, udpCfg);

                //ConnectionFactory cf = (ConnectionFactory)HornetQJMSClient.createConnectionFactoryWithHA(groupConfiguration, JMSFactoryType.CF);

                // We give a little while for each server to broadcast its whereabouts to the client
                //Thread.sleep(2000);

                DiscoveryGroupConfiguration groupConfiguration = new DiscoveryGroupConfiguration(discoverHost, discoverPort);
                org.hornetq.jms.client.HornetQConnectionFactory hqcFactory = HornetQJMSClient.createConnectionFactoryWithHA(groupConfiguration, JMSFactoryType.QUEUE_CF);
                hqcFactory.setReconnectAttempts(-1);
                cFactory = (ConnectionFactory)hqcFactory;
                gwDObj = HornetQJMSClient.createQueue(gwDObjName);
            }else {
                Properties env = new Properties();
                env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                env.put(Context.PROVIDER_URL, "remote://"+labHost+":"+labPort);
                Context jndiContext = new InitialContext(env);
                cFactory = (ConnectionFactory)jndiContext.lookup(cFactoryName);
                gwDObj = (Destination)jndiContext.lookup(gwDObjPath);
                jndiContext.close();
            }

            //session objects have all of the load-balancing and fail-over magic .... only need one Connection object
            connectionObj = cFactory.createConnection();
            connectionObj.setExceptionListener(new ExceptionListener() {
                public void onException(final JMSException e) {
                    log.error("JMSException = "+e.getLocalizedMessage());
                }
            });
            connectionObj.start();
            log.info("main() just created smart hornetq connection to remote broker : "+connectionObj);

            execObj = Executors.newFixedThreadPool(clientCount);
            for(int t=1; t <= clientCount; t++) {
                Runnable threadObj = new JMSClient(new Integer(t));
                execObj.execute(threadObj);
            }

            execObj.shutdown();
            execObj.awaitTermination(1200, TimeUnit.MINUTES);
            log.info("main() all tasks completed on ExecutorService .... server node counts as follows : ");
            Iterator nodeIterator = serverNodeCountHash.keySet().iterator();
            while(nodeIterator.hasNext()) {
                String nodeId = (String)nodeIterator.next();
                log.info("\t"+nodeId+"\t"+serverNodeCountHash.get(nodeId));
            }

        } catch(Throwable x) {
            x.printStackTrace();
        } finally {
            try {
                if(connectionObj != null)
                    connectionObj.close();
            } catch(Exception x) {
                x.printStackTrace();
            }
        }
    }

    private synchronized static int computeTotal(int x) {
        totalCount = totalCount + x;
        return totalCount;
    }

    static class JMSClient implements Runnable{
        private Integer id = 0;
        int counter = 0;
        long threadStart = 0L;
        boolean sessionGood=true;
        Destination tempQueue = null;
        Session sessionObj = null;
        MessageProducer m_sender = null;
        MessageConsumer m_consumer = null;
 
        public JMSClient(Integer id) {
            this.id = id;
        }

        public void run() {
            try {
                sessionObj = connectionObj.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                tempQueue = sessionObj.createTemporaryQueue();
                m_sender = sessionObj.createProducer(gwDObj);
                m_consumer = sessionObj.createConsumer(tempQueue);
                
                // in this particular use-case, not using either a unique Id nor timestamp on the message
                m_sender.setDisableMessageID(true);
                m_sender.setDisableMessageTimestamp(true);

                if(isPersistent)
                    m_sender.setDeliveryMode(DeliveryMode.PERSISTENT);
                else
                    m_sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                threadStart = System.currentTimeMillis();
                String response = null;
                for(counter = 0; counter < requestsPerClient; counter++) {
                    long originalTime = System.currentTimeMillis();
                    TextMessage tMessage = sessionObj.createTextMessage();
                    tMessage.setText("azra and alex");
                    tMessage.setJMSReplyTo(tempQueue);
                    tMessage.setJMSCorrelationID(createRandomString());
                    tMessage.setStringProperty("target", mSelector);
                    if(setDupsStringProperty){
                        tMessage.setStringProperty(org.hornetq.api.core.Message.HDR_DUPLICATE_DETECTION_ID.toString(), id.toString()+counter);
                    }
                    sendMessageAndProcess(tMessage);

                    Thread.sleep(sleepTimeMillis );
                }

                int tCount = computeTotal(counter);
                response = "THREAD_COMPLETE!\t"+id+"\t"+tCount+"\t "+((System.currentTimeMillis() - threadStart)/1000);
                log.info(response);
        
            } catch(Throwable x) {
                log.info("run() id  = "+id+"    :    Throwable = "+x);
                x.printStackTrace();
            } finally {
                try {
                    if(sessionObj != null) {
                        sessionObj.close();
                    }
                } catch(Exception x) {
                    x.printStackTrace();
                }
            }
        }

        private void sendMessageAndProcess(Message tMessage){
            sendMessageAndProcess(tMessage, true);
        }

        private void sendMessageAndProcess(Message tMessage, boolean initialAttempt){
            sendMessage(tMessage);
            TextMessage replyMessage = null;
            try {
                replyMessage = (TextMessage)m_consumer.receive(receiveBlockTimeMillis);
                if(replyMessage == null) {
                    log.error("sendMessageAndProcess() replyMessage # "+counter+" was lost in the sauce.  will re-send cause dupsOk has been set up on consumer");
                    if(initialAttempt) {
                        sendMessageAndProcess(tMessage, false);
                    }else{
                        throw new RuntimeException("sendMessageAndProcess() something fatal has occurred (might want to check for warning messages in hornetq broker log regarding possible duplicate messages ???   ... will exit and not attempt to retry message send");
                    }
                }
            }catch(JMSException x) {
                handleException(x);
            }

            try{
                if(replyMessage != null) {
                    replyMessage.acknowledge();
                    log.info("TRNX_COMPLETE! replyMessage text = "+replyMessage.getText());
                }else{
                    log.error("sendMessageAndProcess() replyMessage was null for counter = "+counter);
                }
            }catch(javax.jms.TransactionRolledBackException x) {
                // as per section 39.2.1.3 of hornetq manual:  Handling Blocking Calls During Failover
                // http://docs.jboss.org/hornetq/2.2.14.Final/user-manual/en/html_single/index.html#ha.automatic.failover.blockingcalls
                log.warn("sendMessageAndProcess() TransactionRolledBackException thrown by hornetq client when attempting to ack receipt of message # "+counter+".  It's possible that the broker did not receive the ack and that the message is still on the broker.  For the purposes of this test client .... not too concerned about this.  May need to flush temp queue");
            }catch(JMSException x) {
                x.printStackTrace();
            }
        }

        private void handleException(JMSException x) {
            x.printStackTrace();
        }

        private void sendMessage(Message tMessage){
            String id = null;
            try {
                id = tMessage.getStringProperty(org.hornetq.api.core.Message.HDR_DUPLICATE_DETECTION_ID.toString());
                m_sender.send(tMessage);
            } catch(JMSException x) {
                if(x.getMessage().contains(TIMED_OUT_WAITING_FOR_RESPONSE)) {
                    // as per section 39.2.1.3 of hornetq manual:  Handling Blocking Calls During Failover
                    // http://docs.jboss.org/hornetq/2.2.14.Final/user-manual/en/html_single/index.html#ha.automatic.failover.blockingcalls
                    log.warn("sendMessage() resending message "+id+" because hornetq client previously responded with:  "+TIMED_OUT_WAITING_FOR_RESPONSE);
                    sendMessage(tMessage);
                }else {
                    x.printStackTrace();
                }
            }
        }
    }



    private static String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

}
