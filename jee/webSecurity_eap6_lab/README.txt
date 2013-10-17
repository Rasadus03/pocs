Purpose :
  1)  demonstrate addition of custom security-domain
  2)  demonstrate configuration of a web archive that references that custom security-domain
  3)  demonstrate various method-level authorization policies (via web.xml) on a rest service

pre-requisistes
  -  in addition to the pre-reqs already mentioned in devPOCs/README.txt, this lab is facilitated with the 'curl' utility
  -  'wget' is another common HTTP utility that can be used in substitute for curl


procedure :
  -  from root of this project, execute 'ant'
  -  tail -f $JBOSS_HOME/standalone/log/server.log
        - notice TRACE level logging on org.jboss.security packages
  -  cat src/main/webapp/WEB-INF/classes/org/jboss/as7/lab/PojoBean.java | more
        - notice the various 'curl' commands to test the various scenarious this lab provides


references
    - https://community.jboss.org/wiki/JBossAS7SecurityDomainModel
    - https://community.jboss.org/wiki/JBossAS7SecureMyWebAppHowDoI
    - http://java.dzone.com/articles/understanding-web-security

future research
    - http://docs.redhat.com/docs/en-US/JBoss_Enterprise_Web_Platform/5/html/RESTEasy_Reference_Guide/Securing_JAX-RS_and_RESTeasy.html
