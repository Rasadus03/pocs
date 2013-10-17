package com.wavechain.jaxws;

import java.util.*;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;

@WebService (targetNamespace="http://jaxws.wavechain.com", name = "jaxwsWsdl_PortType", serviceName = "jaxwsWsdl_Service" )
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public class JaxWS_Service implements IJaxWS_Service {

    private static Logger log = Logger.getLogger(JaxWS_Service.class);
    @Resource WebServiceContext wsContext;

    @WebMethod
    public Echo sanityCheck(Echo eObj) {
        try {
            log.info("echo() input = "+eObj.getPayload());
            eObj.setPayload(eObj.getPayload() + 1);
            return eObj;
        } catch(Throwable x) {
            x.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public HashMap exchangeComplexDataTypes(List x) {
        log.info("exchangeComplexDataTypes() size = "+x.size());
        HashMap newHash = new HashMap();
        newHash.put("key", "value");
        return newHash;
    }
}
