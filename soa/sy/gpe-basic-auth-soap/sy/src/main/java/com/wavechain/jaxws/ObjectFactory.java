//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.19 at 01:34:10 PM MDT 
//


package com.wavechain.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.wavechain.jaxws package. 
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

    private final static QName _ExchangeComplexDataTypes_QNAME = new QName("http://jaxws.wavechain.com", "exchangeComplexDataTypes");
    private final static QName _SanityCheckResponse_QNAME = new QName("http://jaxws.wavechain.com", "sanityCheckResponse");
    private final static QName _ExchangeComplexDataTypesResponse_QNAME = new QName("http://jaxws.wavechain.com", "exchangeComplexDataTypesResponse");
    private final static QName _SanityCheck_QNAME = new QName("http://jaxws.wavechain.com", "sanityCheck");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wavechain.jaxws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExchangeComplexDataTypesResponse }
     * 
     */
    public ExchangeComplexDataTypesResponse createExchangeComplexDataTypesResponse() {
        return new ExchangeComplexDataTypesResponse();
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
     * Create an instance of {@link ExchangeComplexDataTypes }
     * 
     */
    public ExchangeComplexDataTypes createExchangeComplexDataTypes() {
        return new ExchangeComplexDataTypes();
    }

    /**
     * Create an instance of {@link HashMap }
     * 
     */
    public HashMap createHashMap() {
        return new HashMap();
    }

    /**
     * Create an instance of {@link Echo }
     * 
     */
    public Echo createEcho() {
        return new Echo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeComplexDataTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.wavechain.com", name = "exchangeComplexDataTypes")
    public JAXBElement<ExchangeComplexDataTypes> createExchangeComplexDataTypes(ExchangeComplexDataTypes value) {
        return new JAXBElement<ExchangeComplexDataTypes>(_ExchangeComplexDataTypes_QNAME, ExchangeComplexDataTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SanityCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.wavechain.com", name = "sanityCheckResponse")
    public JAXBElement<SanityCheckResponse> createSanityCheckResponse(SanityCheckResponse value) {
        return new JAXBElement<SanityCheckResponse>(_SanityCheckResponse_QNAME, SanityCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeComplexDataTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.wavechain.com", name = "exchangeComplexDataTypesResponse")
    public JAXBElement<ExchangeComplexDataTypesResponse> createExchangeComplexDataTypesResponse(ExchangeComplexDataTypesResponse value) {
        return new JAXBElement<ExchangeComplexDataTypesResponse>(_ExchangeComplexDataTypesResponse_QNAME, ExchangeComplexDataTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SanityCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jaxws.wavechain.com", name = "sanityCheck")
    public JAXBElement<SanityCheck> createSanityCheck(SanityCheck value) {
        return new JAXBElement<SanityCheck>(_SanityCheck_QNAME, SanityCheck.class, null, value);
    }

}
