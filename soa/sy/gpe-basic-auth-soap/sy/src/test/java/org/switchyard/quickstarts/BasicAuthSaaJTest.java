package org.switchyard.quickstarts;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.junit.Test;
import org.switchyard.common.codec.Base64;


public class BasicAuthSaaJTest {

    @Test
    public void testBasicAuthSaaJ() throws Exception {
        
        System.out.println("BasicAuthSOAPMessageComposer.decompose()");
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("jboss");
        sBuffer.append(":");
        sBuffer.append("brms");
        String base64Bytes = Base64.encodeFromString(sBuffer.toString());
        
        MessageFactory mFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        SOAPMessage soapMessage = mFactory.createMessage();
        QName bodyName = new QName("http://jaxws.wavechain.com", "sanityCheck", "jax");
        SOAPBody body = soapMessage.getSOAPBody();
        SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
        
        soapMessage.getMimeHeaders().addHeader("Authorization", "Basic "+base64Bytes);

        QName argName = new QName("arg0");
        SOAPElement argElem = bodyElement.addChildElement(argName);
        QName payloadName = new QName("payload");
        SOAPElement payloadElem = argElem.addChildElement(payloadName);
        payloadElem.addTextNode("24");
        
        
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();
                
        URL endpoint = new URL("http://zareason:8355/helloJaxWS");
        SOAPMessage response = connection.call(soapMessage, endpoint);
        
        connection.close();
        
    }

}
