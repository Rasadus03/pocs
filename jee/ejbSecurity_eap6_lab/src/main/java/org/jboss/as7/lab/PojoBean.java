package org.jboss.as7.lab;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.ejb.SessionContext;

import org.jboss.ejb3.annotation.SecurityDomain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DeclareRoles({IIdentityManagement.OWNER, IIdentityManagement.MANAGER, IIdentityManagement.FINANCE, IIdentityManagement.GUEST})
@RolesAllowed({})
@SecurityDomain("ejbSecurity_eap6_lab")

@Singleton
@Startup
@Remote(IPojoBean.class)
public class PojoBean implements IPojoBean {

    private Logger log = LoggerFactory.getLogger("PojoBean");

    @Resource SessionContext ctx;

    @PostConstruct
    public void start() {
        log.info("start()");
    }

    @PermitAll
    public String sayHello() {
        return "hello "+ctx.getCallerPrincipal().getName(); 
    }

    public String sayHelloAgain() {
        return "hello again "+ctx.getCallerPrincipal().getName(); 
    }

    @RolesAllowed({})
    public String sayGoodbye() {
        return "goodbye "+ctx.getCallerPrincipal().getName(); 
    }

    public String sayGoodbyeAgain() {
        return "goodbye again "+ctx.getCallerPrincipal().getName(); 
    }

    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
