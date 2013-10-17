package com.aimco.esb.actions;

import java.util.Random;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.MessagePayloadProxy;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.message.format.MessageType;
import org.jboss.soa.esb.addressing.eprs.LogicalEPR;
import org.jboss.soa.esb.actions.ActionProcessingFaultException;
import org.jboss.soa.esb.ConfigurationException;

import org.apache.log4j.Logger;

import com.aimco.domain.AimcoDomain;

public class GetSQLJobInfo extends AbstractActionLifecycle {

    private final Logger log = Logger.getLogger("GetSQLJobInfo");
    private ConfigTree _config;
    private Random rGenerator;

    public GetSQLJobInfo(ConfigTree config) throws ConfigurationException {
        _config = config;
        rGenerator = new Random();
    }

    public Message process(Message message) throws ActionProcessingFaultException {
        log.info("jbpmProcessDefName: " + message.getBody().get("jbpmProcessDefName"));
        int newVar = rGenerator.nextInt(5);
        AimcoDomain dObj = null;
        Object obj = message.getBody().get("aimcoDomain");
        if(obj != null) {
            dObj = (AimcoDomain)obj;
            log.info("process() original aimcoDomain = "+ dObj +" : new aimcoVar = "+newVar);
            dObj.setAimcoVar(newVar);
        } else {
            log.info("process() aimco domain is null");
        }
        try {
            Thread.sleep(10000);
        } catch(Exception x) {
            x.printStackTrace();
        }
        log.info("process() done sleeping");
        return message;
    }
}
