package org.jboss.gpse;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;
import javax.transaction.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor @Transactional
public class TransactionInterceptor {
    
    @Resource UserTransaction uTrnx;
    private Logger log = LoggerFactory.getLogger("TransactionInterceptor");

    public TransactionInterceptor() {
    }

    @AroundInvoke
    public Object invoke(InvocationContext ic) throws Exception {
        try {
            uTrnx.begin();
            log.info("invoke() before method = "+ic.getMethod().getName()+" : trnx status = "+uTrnx.getStatus());
            return ic.proceed();
        }catch(Exception x){
            if(uTrnx.getStatus() == Status.STATUS_ACTIVE)
                uTrnx.rollback();
            throw x;
        }finally{
            if(uTrnx.getStatus() == Status.STATUS_ACTIVE)
                uTrnx.commit();
            log.info("invoke() after method = "+ic.getMethod().getName()+" trnx status = "+uTrnx.getStatus());
        }
    }
}
