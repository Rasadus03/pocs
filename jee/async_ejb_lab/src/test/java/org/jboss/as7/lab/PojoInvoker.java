package org.jboss.as7.lab;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ejb.embeddable.EJBContainer;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.as7.lab.Pojo;
import org.jboss.as7.lab.IPojoBean;

public class PojoInvoker {

    public static final String EJB_URL = "org.jboss.as7.lab.ejb.url";

    public static void main(String args[]) throws ExecutionException, javax.naming.NamingException, java.lang.InterruptedException {
        
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

            Future<Integer> futureResult = pBean.multiplyAndReturn(8, 9);
            System.out.println("main() invoked remote multipyAndReturn .... will do other stuff  ..... ok, now ready to check results of multiplyAndReturn ");
            Integer intResult = futureResult.get();
            System.out.println("The prior async method returned " + intResult);

            //pBean.multiplyAndIgnore(2,3);
            //System.out.println("just invoked multiplyAndIgnore");
        }finally {
            try {
                if(jndiContext != null)
                    jndiContext.close();
            }catch(Exception x){ x.printStackTrace(); }
        }
    }
}

