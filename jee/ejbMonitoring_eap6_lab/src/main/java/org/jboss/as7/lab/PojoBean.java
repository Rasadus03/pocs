package org.jboss.as7.lab;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.ejb.SessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public String sayHello() {
        return "hello "; 
    }

    public String sayHelloAgain() {
        return "hello again "; 
    }

    public String sayGoodbye() {
        return "goodbye "; 
    }

    public String sayGoodbyeAgain() {
        return "goodbye again "; 
    }

    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
