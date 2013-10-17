package org.jboss.gpse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/")
public class PojoService {

    /**
     * sample usage :
     *  curl -HAccept:text/plain $HOSTNAME:8080/rs-easy/pojo
    */
    @GET
    @Path("/pojo")
    @Produces({ "text/plain" })
    public Response createPojo() {
        ResponseBuilder builder = Response.ok("great success!\n");
        return builder.build();
    }

}
