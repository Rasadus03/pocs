PURPOSE :
  - demonstrate configuration of Qpid resource adapter in JBoss EAP 6


PROCEDURE :
  - clone qpid source code repo
        git clone git://github.com/apache/qpid.git
    
  - build qpid java branch (which includes new jca functionality)
        cd qpid/java
        git checkout trunk
        ant release

  - review Qpid JCA documentation
        vi jca/README.txt
   
  - build this project and provision EAP 6 with Qpid JCA resource adapter
    cd <root of this project>
    vi build.properties  (review and modify as appropriate)
    ant

  - send a simple message to the C++ broker on the queue that the MDB is listening on
    ant simpleInvoker

  - review EJB stats provided by EAP 6 EJB container
    ant ejbStats
