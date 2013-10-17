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

import com.aimco.domain.AimcoDomain;

public class DownloadIncrementalFile extends AbstractActionLifecycle {

    private final Logger log = Logger.getLogger("DownloadIncrementalFile");
    private ConfigTree _config;

    public DownloadIncrementalFile(ConfigTree config) throws ConfigurationException {
        _config = config;
    }

    public Message process(Message message) throws ActionProcessingFaultException {
        AimcoDomain dObj = new AimcoDomain();
        message.getBody().add("aimcoDomain", dObj);
        return message;
    }
}
