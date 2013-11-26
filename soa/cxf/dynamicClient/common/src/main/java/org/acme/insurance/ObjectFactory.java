package org.acme.insurance;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    //private final static QName _ReviewQuoteResponse_QNAME = new QName("urn:com.redhat.gpe.auditReview:1.0", "reviewQuoteResponse");
    //private final static QName _ReviewQuote_QNAME = new QName("urn:com.redhat.gpe.auditReview:1.0", "reviewQuote");
    private final static QName _Policy_QNAME = new QName("urn:com.redhat.gpe.auditReview:1.0", "policy");

    public ObjectFactory() {
    }
    /*
    public ReviewQuoteResponse createReviewQuoteResponse() {
        return new ReviewQuoteResponse();
    }
    public ReviewQuote createReviewQuote() {
        return new ReviewQuote();
    }
    */
    public Policy createPolicy() {
        return new Policy();
    }
    public Driver createDriver() {
        return new Driver();
    }
    /*
    @XmlElementDecl(namespace = "urn:com.redhat.gpe.auditReview:1.0", name = "reviewQuoteResponse")
    public JAXBElement<ReviewQuoteResponse> createReviewQuoteResponse(ReviewQuoteResponse value) {
        return new JAXBElement<ReviewQuoteResponse>(_ReviewQuoteResponse_QNAME, ReviewQuoteResponse.class, null, value);
    }
    @XmlElementDecl(namespace = "urn:com.redhat.gpe.auditReview:1.0", name = "reviewQuote")
    public JAXBElement<ReviewQuote> createReviewQuote(ReviewQuote value) {
        return new JAXBElement<ReviewQuote>(_ReviewQuote_QNAME, ReviewQuote.class, null, value);
    }
    */
    @XmlElementDecl(namespace = "urn:com.redhat.gpe.auditReview:1.0", name = "policy")
    public JAXBElement<Policy> createPolicy(Policy value) {
        return new JAXBElement<Policy>(_Policy_QNAME, Policy.class, null, value);
    }
}
