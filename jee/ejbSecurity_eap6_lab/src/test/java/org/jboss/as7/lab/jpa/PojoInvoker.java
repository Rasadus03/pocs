package org.jboss.as7.lab.jpa;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.security.auth.login.LoginContext;

import org.jboss.as7.lab.IPojoBean;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;

public class PojoInvoker {

    public static final String EJB_URL = "org.jboss.as7.lab.ejb.url";

    public static void main(String args[]) {
        
        if(System.getProperty(EJB_URL) == null)
            throw new RuntimeException("main() must pass system property : "+ EJB_URL);
        String ejbUrl = System.getProperty(EJB_URL);
        System.out.println("main() ejbUrl = "+ejbUrl);

        Context jndiContext = null;
        try {
/*
            SecurityClient client = SecurityClientFactory.getSecurityClient();
            client.setSimple("guest", "guest");
            client.login();
*/
            LoginContext lc = Util.getCLMLoginContext("guest", "guest");
            lc.login();

            Properties jndiProps = new Properties();
            if(System.getProperty("java.naming.provider.url") != null) {
                System.out.println("main() 'remote:' client.  java.naming.provider.url = "+System.getProperty("java.naming.provider.url"));
                jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
                jndiProps.put(Context.PROVIDER_URL, System.getProperty("java.naming.provider.url"));
            } else {
                System.out.println("main() will invoker as per 'ejb:' client");
                jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            }

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

