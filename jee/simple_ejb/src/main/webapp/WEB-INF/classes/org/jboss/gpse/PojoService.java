package org.jboss.gpse;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ejb.Stateless;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Path("/")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PojoService {

        /**
         * sample usage :
         *  curl -X PUT -HAccept:text/plain $HOSTNAME:8080/jpa_cdi_lab/pojo/2
         */
	@PUT
	@Path("/pojo/{pojoId}")
	@Produces({ "text/plain" })
        @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
        //@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response createPojo(@PathParam("pojoId") final String pojoId) {
            UserTransaction uTrnx = null;
            try {
                uTrnx = (UserTransaction)new InitialContext().lookup("java:jboss/UserTransaction");
                System.out.println("uTrnx= "+uTrnx);
                uTrnx.begin();
                if(true){
                    uTrnx.setRollbackOnly();
                    throw new RuntimeException("this sucks");
                }
            
                Pojo testPojo = new Pojo();
                testPojo.setId(pojoId);
                ResponseBuilder builder = Response.ok("great success!");
                uTrnx.commit();
                return builder.build();
            }catch(Exception x) {
                ResponseBuilder builder = null;
                try {
                    System.out.println("transaction status = "+uTrnx.getStatus());
                    uTrnx.rollback();
                    builder = Response.ok("failure = "+x.getLocalizedMessage());
                }catch(Throwable y) {
                    builder = Response.ok("failure: this really sucks");
                }
                return builder.build();
            }
	}

        /**
     * sample usage :
     *  curl -X GET -HAccept:text/plain localhost:8080/jpa_cdi_lab/sanityCheck
     */
    @GET
    @Path("/sanityCheck")
    @Produces({ "text/plain" })
    public Response sanityCheck() {
        ResponseBuilder builder = Response.ok("good to go\n");
        return builder.build();
    }


}
