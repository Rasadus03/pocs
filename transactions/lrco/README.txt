PURPOSE :
  - demonstrate Last Resource Commit Optimzation provided by JBoss TS using :
    1) local trnx Hornetq MDB
    2) xa-data-source to PostgreSQL database


BACKGROUND
    - in situations where there will only be two resources enlisted in a transaction,
      you'll want to use XA but only one of those resources needs to be XA compliant.
      The other resource does not need to be XA compliant so as to avoid additional
      overhead involved in using the XA features of that resource.
      ie: the additional shared memory requirements of enabling XA in PostgreSQL and
      the additional "prepare" phase statements to that PostgreSQL database

    - https://community.jboss.org/wiki/Multiple1PC
    - section 32.1.1. Using Container-Managed Transactions of Hornetq User Manual


PROCEDURE :
  - ensure JBoss EAP 6 is installed at $JBOSS_HOME
  - review  :   build.properties
  - execute :   ant                 (invokes jboss.provision by default)
  - execute :   ant jmsLoadTest

To-Do :
  - implement a 'control' exchange between consumer and producer to indicate beginning and end of test
  - provide final load test report

