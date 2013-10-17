package org.jboss.as7.lab;

import java.util.Properties;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.as7.lab.IPojoBean;

public class PojoInvoker {

    public static final String EJB_URL = "org.jboss.as7.lab.ejb.url";

    public static void main(String args[]) {
        
        if(System.getProperty(EJB_URL) == null)
            throw new RuntimeException("main() must pass system property : "+ EJB_URL);

        String ejbUrl = System.getProperty(EJB_URL);
        System.out.println("main() ejbUrl = "+ejbUrl);

        Context jndiContext = null;
        try {
            Properties jndiProps = new Properties();
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiContext = new InitialContext(jndiProps);

            IPojoBean pBean = (IPojoBean)jndiContext.lookup(ejbUrl);

            System.out.println("main() invoking sayHello : "+pBean.sayHello());
            System.out.println("main() invoking sayGoodbye : "+pBean.sayGoodbye());

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

