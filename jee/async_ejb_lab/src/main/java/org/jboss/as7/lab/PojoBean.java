package org.jboss.as7.lab;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.DependsOn;
import javax.ejb.Startup;
import java.util.concurrent.Future;
import javax.ejb.Asynchronous;
import javax.ejb.AsyncResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
@Remote(IPojoBean.class)
@Asynchronous
public class PojoBean implements IPojoBean {

    private Logger log = LoggerFactory.getLogger("PojoBean");
    private int sleepTimeMillis = 1000;

    @PostConstruct
    public void start() {
        sleepTimeMillis = Integer.parseInt(System.getProperty("org.jboss.SLEEP_TIME"));
        log.info("start() sleepTimeMillis = "+sleepTimeMillis);
    }

    public void multiplyAndIgnore(int a, int b) {
        log.info("multiplyAndIgnore()");
    }

    public Future<Integer> multiplyAndReturn(int a, int b) {
        log.info("multiplyAndReturn() ");
        try {
            Thread.sleep(sleepTimeMillis);
        } catch(Exception x) {
            throw new RuntimeException(x);
        }
        return new AsyncResult<Integer>(a * b);
    }


    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
