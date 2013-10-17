
package org.switchyard.quickstarts;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.switchyard.quickstarts package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SanityCheckResponse_QNAME = new QName("quickstarts.switchyard.org", "sanityCheckResponse");
    private final static QName _SanityCheck_QNAME = new QName("quickstarts.switchyard.org", "sanityCheck");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.switchyard.quickstarts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SanityCheck }
     * 
     */
    public SanityCheck createSanityCheck() {
        return new SanityCheck();
    }

    /**
     * Create an instance of {@link SanityCheckResponse }
     * 
     */
    public SanityCheckResponse createSanityCheckResponse() {
        return new SanityCheckResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SanityCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "quickstarts.switchyard.org", name = "sanityCheckResponse")
    public JAXBElement<SanityCheckResponse> createSanityCheckResponse(SanityCheckResponse value) {
        return new JAXBElement<SanityCheckResponse>(_SanityCheckResponse_QNAME, SanityCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SanityCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "quickstarts.switchyard.org", name = "sanityCheck")
    public JAXBElement<SanityCheck> createSanityCheck(SanityCheck value) {
        return new JAXBElement<SanityCheck>(_SanityCheck_QNAME, SanityCheck.class, null, value);
    }

}
