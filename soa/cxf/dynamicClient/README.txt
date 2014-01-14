Overview
    - CXF JaxWsDynamicClientFactory is the implementation used in BPMS6 org.jbpm.process.workitem.bpmn2.ServiceTaskHandler
    - This project isolates just the CXF client invoking a SOAP service

1)  start EAP6.* in standalone mode
2)  clone this project from github and cd into this directory
3)  mvn clean install -Pcommunity
4)  cp service/target/auditReview.war $JBOSS_HOME/standalone/deployments
5)  cd client
6)  mvn clean test -Pcommunity
    - should see the following stack trace on the client:

    Caused by: java.lang.ClassCastException: org.acme.insurance.Policy cannot be cast to auditreview.gpe.redhat.com._1.Policy

    - seems that CXF dynamic client is generating client proxy classes whose package name is:  auditreview.gpe.redhat.com._1
    - to invoke the remote SOAP service, package name should be:  org.acme.insurance
    - how can CXF be configured to specify a custom package name for dynamically generated client classes ?
        - essentially the equivalent of passing the '-p' flag to:  $JBOSS_HOME/bin/wsconsume.sh 




