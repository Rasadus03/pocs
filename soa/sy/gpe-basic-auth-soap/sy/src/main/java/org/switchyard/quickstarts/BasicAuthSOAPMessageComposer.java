package org.switchyard.quickstarts;

import javax.xml.soap.SOAPMessage;

import org.switchyard.common.codec.Base64;
import org.switchyard.component.common.label.EndpointLabel;
import org.switchyard.component.soap.composer.SOAPBindingData;
import org.switchyard.component.soap.composer.SOAPContextMapper;
import org.switchyard.component.soap.composer.SOAPMessageComposer;
import org.switchyard.Exchange;
import org.switchyard.Message;

public class BasicAuthSOAPMessageComposer extends SOAPMessageComposer {
    
    public Message compose(SOAPBindingData source, Exchange exchange) throws Exception {
        System.out.println("BasicAuthSOAPMessageComposer.compose()");
        return super.compose(source, exchange);
    }
    
    public SOAPBindingData decompose(Exchange exchange, SOAPBindingData target) throws Exception {
        System.out.println("BasicAuthSOAPMessageComposer.decompose()");
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("jboss");
        sBuffer.append(":");
        sBuffer.append("brms");
        String eBytes = Base64.encodeFromString(sBuffer.toString());
        String authHeaderValue = "Basic "+eBytes;
        //SOAPMessage soapMessage = target.getSOAPMessage();
        //soapMessage.getMimeHeaders().addHeader("Authorization", authHeaderValue);
        
        exchange.getContext().setProperty("Authorization", authHeaderValue).addLabels(new String[]{EndpointLabel.HTTP.label()});
        exchange.getContext().setProperty("TestHeader", "azra and alex").addLabels(new String[]{EndpointLabel.HTTP.label()});
         
        return super.decompose(exchange, target);
    }
}
