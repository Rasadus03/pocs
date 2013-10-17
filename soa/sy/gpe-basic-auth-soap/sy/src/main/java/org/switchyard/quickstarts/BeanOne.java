package org.switchyard.quickstarts;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import org.switchyard.annotations.Transformer;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;
import org.w3c.dom.Node;

import com.wavechain.jaxws.Echo;
import com.wavechain.jaxws.IJaxWS_Service;

@Service(IBeanOne.class)
public class BeanOne implements IBeanOne {
	
	@Inject @Reference("IJaxWS_Service")
	private IJaxWS_Service jaxwsService;

    public String sanityCheck(String inboundString) {
        System.out.println("BeanOne.sanityCheck() inboundString = "+inboundString);
        Echo eObj = new Echo();
        eObj.setPayload(10);
        com.wavechain.jaxws.SanityCheck sObj = new com.wavechain.jaxws.SanityCheck();
        sObj.setArg0(eObj);
        com.wavechain.jaxws.SanityCheckResponse sObjResponse = jaxwsService.sanityCheck(sObj);
        System.out.println("BeanOne.sanityCheck() jaxResponse = "+sObjResponse.getReturn().getPayload());
        return inboundString;
    }
    
    /* a jaxb transform exists from {quickstarts.switchyard.org}/sanityCheck DOMSource --> java.lang.String
     * still need this transformer for:  java:org.switchyard.quickstarts.SanityCheck --> java.lang.String
     */
    @Transformer(from="java:org.switchyard.quickstarts.SanityCheck")
    public String transform(SanityCheck sCheck){
        return sCheck.getString();
    }
    
    /* Can't marshal (via JAXB) a String back to a SOAP client cause a String does not include any JAXB annotations
     * subsequently, this transform executes the full String ---> javax.xml.soap.SOAPBody
     */
    @Transformer(to="{quickstarts.switchyard.org}sanityCheckResponse")
    public Node transform(String x){
        try {
        	MessageFactory mFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage message = mFactory.createMessage();
            QName bodyName = new QName("quickstarts.switchyard.org", "sanityCheck", "quic");
            SOAPBody body = message.getSOAPBody();
            SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
            
            
            QName textName = new QName("string");
            SOAPElement textElem = bodyElement.addChildElement(textName);
            textElem.addTextNode(x);
            
            return body;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
