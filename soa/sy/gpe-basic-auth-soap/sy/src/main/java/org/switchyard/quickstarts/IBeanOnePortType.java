
package org.switchyard.quickstarts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IBeanOnePortType", targetNamespace = "quickstarts.switchyard.org")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IBeanOnePortType {


    /**
     * 
     * @param string
     */
    @WebMethod(action = "sanityCheck")
    @RequestWrapper(localName = "sanityCheck", targetNamespace = "quickstarts.switchyard.org", className = "org.switchyard.quickstarts.SanityCheck")
    @ResponseWrapper(localName = "sanityCheckResponse", targetNamespace = "quickstarts.switchyard.org", className = "org.switchyard.quickstarts.SanityCheckResponse")
    public void sanityCheck(
        @WebParam(name = "string", targetNamespace = "quickstarts.switchyard.org", mode = WebParam.Mode.INOUT)
        Holder<String> string);

}
