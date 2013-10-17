package com.redhat.gpse;

import java.io.InputStream;

import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;

import org.apache.log4j.Logger;


public class SaxParserApproach {

	private static final String SET_SCHEMA_LOCATION_PROPERTY="com.redhat.gpse.set.schema.location.property";
	private static final String SCHEMA_LOCATION_PROPERTY_VALUE="com.redhat.gpse.schema.location.property.value";
	private static final Logger log = Logger.getLogger("SaxParserApproach");
	
    private static final String xmlFile = "/xml/orders.xml";
    private static boolean setSchemaLocationProperty = false;
    private static String schemaLocationPropertyValue=null;

    public static void main(String args[]) throws Exception {
    	
    	InputStream xmlInstanceStream = null;
        try {
        	setSchemaLocationProperty = Boolean.parseBoolean(System.getProperty(SET_SCHEMA_LOCATION_PROPERTY, "FALSE"));
            schemaLocationPropertyValue = System.getProperty(SCHEMA_LOCATION_PROPERTY_VALUE);
            if(schemaLocationPropertyValue == null)
            	throw new RuntimeException("main() must set value for system property: "+SCHEMA_LOCATION_PROPERTY_VALUE);
        	
        	log.info("main() setSchemaLocationProperty = "+setSchemaLocationProperty+" : schemaLocationPropertyValue = "+schemaLocationPropertyValue);
        	
        	SAXParser parser = new SAXParser();
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setFeature("http://apache.org/xml/features/validation/schema", true);
            parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            if(setSchemaLocationProperty)
                parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", schemaLocationPropertyValue);

            xmlInstanceStream = SaxParserApproach.class.getResourceAsStream(xmlFile);
            if(xmlInstanceStream == null)
            	throw new RuntimeException("main() following file not on the classpath: "+xmlFile);
            InputSource xmlInstanceSource = new InputSource(xmlInstanceStream);
            
            Validator handler = new Validator();
            parser.setErrorHandler(handler);
            parser.parse(xmlInstanceSource);
            
            log.info("main() the following file validates correctly: "+xmlFile);
        }finally {
            if(xmlInstanceStream != null)
            	xmlInstanceStream.close();
        }
    }

    static class Validator extends DefaultHandler {
    	public boolean validationError = false;
    	public SAXParseException saxParseException = null;

    	public void error(SAXParseException exception) throws SAXException {
    		validationError = true;
    		saxParseException = exception;
    		throw exception;
    	}

    	public void fatalError(SAXParseException exception) throws SAXException {
    	    validationError = true;
    	    saxParseException = exception;
    	    throw exception;
    	}

    	public void warning(SAXParseException exception) throws SAXException {
    		exception.printStackTrace();
    	}
    }
}
