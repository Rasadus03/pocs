purpose:
  -  demonstrate two or more embedded Hornetq brokers configured for HA in domain mode
  -  demonstrate a MDB remotely consuming messages from whichever hornetq broker is active and seemlessly failing over when need be
  -  demonstrate a JMS client connection sending messages to whichever hornetq broker is active and seemlessly failing over when need be
  -  demonstrate handling of JMSExceptions that are thrown when broker failover occurs and the client connection is blocking waiting for a response
  -  demonstrate set-up of dups message detection ... which is one of the settings needed for once & only once delivery guarantees without the need for an XA trnx manager
  -  demonstrate configuration of discovery settings of all hornetq brokers and clients via a single, centralized property that can be modified at deployment

usage:
  1)  review and modify build.properties at root of this project
  2)  execute:   ant
        - notice the following servers in $JBOSS_HOME/domain-lab/servers :

        lab-full-ha-0   :   live broker
        lab-full-ha-1   :   backup broker
        lab-core        :   MDB host;  MDB sends a reply message back to test client on a temporary queue

  3)  tail -f $JBOSS_HOME/domain-lab/servers/lab-core/log/server.log
  4)  execute:   ant jmsTest
  5)  ps -aef | grep lab-full-ha-0
  6)  kill -9 <process id of lab-full-ha-0 >


exepected results:
  - MDB and JMS producer (in MDB) should discovery brokers (and download broker topology) via UDP  (not via a hardcoded TCP remoting address)
  - MDB, JMS producer in MDB and jms test client should all seemlessly failover to backup hornetq broker once primary broker is killed

additional notes:
  1)  this setup uses a single domain profile (full-ha) to define the single server-group that all hornetq brokers will be a part of.
      there are trade-offs to this approach :
        a)  easier configuration as only one profile is defined (as opposed to having to define a unique profile for each server)  
        b)  not able to explicitly define which server is the live and which server(s) are the backup
        c)  automatic failback behavior does not work
            - would need to resort to manual failback, ie:
                1)  after lab-full-ha-0 has been killed, execute:    ant local.start.server -Dserver.id=lab-full-ha-0
                2)  kill -9 <process id of lab-full-ha-1
                3)  execute:   ant local.start.server -Dserver.id=lab-full-ha-1  
