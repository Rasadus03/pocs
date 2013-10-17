pre-requisites
    1)  pfp provisioned environemnt with sy-core-group
    2)  pfp provisioned environment is running

1)  mvn clean install -Dmaven.test.skip=true

2)  $JBOSS_HOME/bin/jboss-cli.sh --connect --controller=zareason:9999
    [domain@zareason:9999 /] deploy jaxws/target/helloJaxWS.war --server-groups=sy-core-group --name=helloJaxWS.war
    [domain@zareason:9999 /] deploy sy/target/gpe-basic-auth-soap-sy.jar --server-groups=sy-core-group --name=auth-soap.jar  

    NOTE:  helloJaxWS web archive is secured using BASIC authentication via the 'other' security domain
           out-of-the-box credentials to authenticate in a PFP environement are:   jboss / brms

3)  start soap-ui and send message to http://zareason:8355/BeanOne/IBeanOne?wsdl

    will see stack trace of the following in sy server.log:

        Caused by: org.apache.cxf.transport.http.HTTPException: HTTP response '401: Unauthorized' when communicating with http://zareason:8355/helloJaxWS
    at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponseInternal(HTTPConduit.java:1621) [cxf-rt-transports-http-2.6.6-redhat-3.jar:2.6.6-redhat-3]
    at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.handleResponse(HTTPConduit.java:1532) [cxf-rt-transports-http-2.6.6-redhat-3.jar:2.6.6-redhat-3]
    at org.apache.cxf.transport.http.HTTPConduit$WrappedOutputStream.close(HTTPConduit.java:1440) [cxf-rt-transports-http-2.6.6-redhat-3.jar:2.6.6-redhat-3]

    hopefully this will be resolved soon as per:  https://community.jboss.org/message/820205#820205

