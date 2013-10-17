PURPOSE :
  - load test Hornetq broker 
  - hornetq client provided in this test suite can invoke broker ConnectionFactory and Destinations acquired via one of the following :
    1)  JMS 
    2)  Hornetq proprietary (non-jms)


PROCEDURE :
  - ensure JBoss EAP 6 is installed at $JBOSS_HOME
  - review  :   build.properties
  - execute :   ant                 (invokes jboss.provision by default)
  - execute :   ant jmsLoadTest

To-Do :
  - implement a 'control' exchange between consumer and producer to indicate beginning and end of test
  - provide final load test report

