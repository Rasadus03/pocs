package com.redhat.gpse;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;

public class StreamSourceArray {

	private static final String TEST_SCHEMA_COMBINED ="com.redhat.gpse.test.schema.combined";
    private static final String schemaFile0 = "/xsd/order_types.xsd";
    private static final String schemaFile1 = "/xsd/orders.xsd";
    private static final String xmlFile = "/xml/orders.xml";
    
    private static final Logger log = Logger.getLogger("StreamSourceArray");

    private static boolean testSchemaCombined = true;

    public static void main(String args[]) throws Exception {

        InputStream schema0 = null;
        InputStream schema1 = null;
        InputStream xmlStream = null;
        try {
            testSchemaCombined = Boolean.parseBoolean(System.getProperty(TEST_SCHEMA_COMBINED, "TRUE"));
            log.info("main() testSchemaCombined = "+testSchemaCombined);

            // 1)  schema prep
            schema0 = StreamSourceArray.class.getResourceAsStream(schemaFile0);
            if(schema0 == null)
                throw new RuntimeException("main() following schema not found on classpath: "+schemaFile0);
            schema1 = StreamSourceArray.class.getResourceAsStream(schemaFile1);
            if(schema1 == null)
                throw new RuntimeException("main() following schema not found on classpath: "+schemaFile1);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = null;
            if(testSchemaCombined) {
                schema = schemaFactory.newSchema(new StreamSource(schema1));
            }else {
                StreamSource[] schemas = new StreamSource[2];
                schemas[0] = new StreamSource(schema0);
                schemas[1] = new StreamSource(schema1);
                schema = schemaFactory.newSchema(schemas);
            }


            // 2)  xml instance prep
            xmlStream = StreamSourceArray.class.getResourceAsStream(xmlFile);
            if(xmlStream == null)
                throw new RuntimeException("main() following xml not found on classpath: "+xmlFile);

            // 3)  validate
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            System.out.println("main() the following file validates correctly: "+xmlFile);
        }finally {
            if(schema0 != null) schema0.close();
            if(schema1 != null) schema1.close();
            if(xmlStream != null) xmlStream.close();
        }
    }
}
