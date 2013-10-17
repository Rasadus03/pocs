PURPOSE :
  - a JTA transaction (and the two phase commit (2PC) overhead that is normally associated with JTA) is not alway necessary
  - in a situation where a MDB is the only resource involved in a transaction, then a local trnx (which does not incur overhead of 2PC) can be an option
  - this lab demonstrates development of a MDB that utilizes a local transaction


BACKGROUND
    - https://community.jboss.org/wiki/Multiple1PC
    - section 32.1.1. Using Container-Managed Transactions of Hornetq User Manual
    - http://www.postgresql.org/docs/8.3/static/runtime-config-resource.html


PROCEDURE :
  - ensure JBoss EAP 6 is installed at $JBOSS_HOME
  - review  :   build.properties
  - execute :   ant                 (invokes jboss.provision by default)
  - execute :   ant jmsLoadTest

To-Do :
  - implement a 'control' exchange between consumer and producer to indicate beginning and end of test
  - provide final load test report

