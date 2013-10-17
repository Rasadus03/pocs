package com.aimco.esb.actions;

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

public class FireSQLRules extends AbstractActionLifecycle {

    private final Logger log = Logger.getLogger("FireSQLRules");
    private ConfigTree _config;

    public FireSQLRules(ConfigTree config) throws ConfigurationException {
        _config = config;
    }

    public Message process(Message message) throws ActionProcessingFaultException {
        log.info("Token ID: " + message.getBody().get("jbpmTokenId"));
        log.info("process() aimcoDomain = "+message.getBody().get("aimcoDomain"));
        return message;
    }
}
