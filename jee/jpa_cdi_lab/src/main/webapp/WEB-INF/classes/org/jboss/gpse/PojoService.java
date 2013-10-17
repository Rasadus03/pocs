package org.jboss.gpse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/")
public class PojoService {

	@Inject
	PojoBean pojoBean;

        /**
         * sample usage :
         *  curl -X PUT -HAccept:text/plain $HOSTNAME:8080/jpa_cdi_lab-web/pojo/2
         */
	@PUT
	@Path("/pojo/{pojoId}")
	@Produces({ "text/plain" })
	public Response createPojo(@PathParam("pojoId") final String pojoId) {
            Pojo testPojo = new Pojo();
            testPojo.setId(pojoId);
            pojoBean.createPojo(testPojo);
            ResponseBuilder builder = Response.ok("great success!");
            return builder.build();
	}

}
