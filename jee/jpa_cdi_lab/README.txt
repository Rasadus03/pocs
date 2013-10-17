PURPOSE :
  - demonstrates use of a CDI bean operating within scope of a JTA transaction introduced via JSR-318 interceptors

NOTES :
  - includes a JAX-RS service injected with a CDI bean
  - CDI bean is subsequently injected with a JPA JTA-enabled EntityManagerFactory
  - CDI bean also operates within scope of a JTA transaction introduced via a JSR-318 "TransactionInterceptor"
  - when invoked, 'createPojo(...)' function of CDI bean persists a Pojo object into a postgresql database


PROCEDURE :
  - build and deploy this project
    cd <root of this project>
    vi build.properties  (review and modify as appropriate)
    ant

  - invoke REST service (which subsequently invokes injected CDI bean)
    curl -X PUT -HAccept:text/plain $HOSTNAME:8080/jpa_cdi_lab-web/pojo/2

EXPECTED OUTPUT :
  - EAP 6 log file 
    19:45:31,597 INFO  [TransactionInterceptor] invoke() before method = createPojo : trnx status = 0
    19:45:31,812 INFO  [TransactionInterceptor] invoke() after method = createPojo trnx status =   6

  - postgresql
    test=# select * from pojo;
         id | name 
        ----+------
         2  | 
        (1 row)
