package org.jboss.as7.lab;

import java.util.Properties;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.as7.lab.Pojo;
import org.jboss.as7.lab.IPojoBean;

public class PojoInvoker {

    public static final String EJB_URL = "org.jboss.as7.lab.ejb.url";
    public static final String TEST_USING_JTA_ENABLED_EMF = "testUsingJTAEnabledEMF";

    public static void main(String args[]) {
        
        if(System.getProperty(EJB_URL) == null)
            throw new RuntimeException("main() must pass system property : "+ EJB_URL);

        if(System.getProperty(TEST_USING_JTA_ENABLED_EMF) == null)
            throw new RuntimeException("main() must pass system property : "+ TEST_USING_JTA_ENABLED_EMF);

        String ejbUrl = System.getProperty(EJB_URL);
        Boolean testUsingJTAEnabledEMF = Boolean.parseBoolean(System.getProperty(TEST_USING_JTA_ENABLED_EMF));
        System.out.println("main() ejbUrl = "+ejbUrl+" : "+ testUsingJTAEnabledEMF);

        Context jndiContext = null;
        try {
            Properties jndiProps = new Properties();
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiContext = new InitialContext(jndiProps);

            IPojoBean pBean = (IPojoBean)jndiContext.lookup(ejbUrl);

            Random rGenerator = new Random();
            Pojo testPojo = new Pojo();
            String id = "pojoTest_"+rGenerator.nextInt();
            testPojo.setId(id);
            if(testUsingJTAEnabledEMF)
                pBean.createPojoViaJTAEntityManager(testPojo);
            else
                pBean.createPojoViaResourceLocalEntityManager(testPojo);

            System.out.println("main() just posted a new Pojo obj with id = "+id+" : testUsingJTAEnabledEMF = "+testUsingJTAEnabledEMF);
        } catch(Exception x) {
            throw new RuntimeException(x);
        }finally {
            try {
                if(jndiContext != null)
                    jndiContext.close();
            }catch(Exception x){ x.printStackTrace(); }
        }
    }
}

