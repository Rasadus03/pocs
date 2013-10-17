package org.jboss.as7.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class PojoBean {

    private static Logger log = LoggerFactory.getLogger("PojoBean");

    /* 
      - A servlet configuration object used by a servlet container to pass 'init-param' configs from web.xml to a servlet during initialization
      - there is only one ServletConfig per servlet
    */
    @Context ServletConfig servletConfig;

    /*
      - used to access 'context-param' elements configured in web.xml
    */
    @Context ServletContext servletContext;

    @Context SecurityContext securityContext;

    // NOTE:  this lifecycle annotation is ignored in jax-rs
    @PostConstruct
    public void start() {
        log.info("start() ");
    }

    public PojoBean() {
        log.info("constructor() this = "+this);
    }


    /**
     * Useage :  curl --user guest:guest1 $HOSTNAME:8080/webSecurity_eap6_lab/unchecked/permitAllResource
     */
    @GET
    @Path("unchecked/permitAllResource")
    @Produces("text/plain")
    public Response permitAll() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("permitAll() \n\tprincipal = "+securityContext.getUserPrincipal());
        sBuilder.append("\n\tservlet config = "+servletConfig);
        sBuilder.append("\n\tservlet context = "+servletContext);
        log.info(sBuilder.toString());
        ResponseBuilder builder = Response.ok("great success!");
        return builder.build();
    }


    /**
     * Useage :  curl --user owner:owner1 $HOSTNAME:8080/webSecurity_eap6_lab/checked/rolesAllowedResource
     */
    @GET
    @Path("/checked/rolesAllowedResource")
    @Produces("text/plain")
    public Response rolesAllowed() {
        log.info("rolesAllowed() principil = "+securityContext.getUserPrincipal());
        ResponseBuilder builder = Response.ok("great success!");
        return builder.build();
    }

    /**
     * Useage :  curl $HOSTNAME:8080/webSecurity_eap6_lab/offlimits/denyAll
     */
    @GET
    @Path("/offlimits/denyAll")
    @Produces("text/plain")
    public Response denyAll() {
        log.info("denyAll() principil = "+securityContext.getUserPrincipal());
        ResponseBuilder builder = Response.ok();
        return builder.build();
    }
    

    // NOTE:  this lifecycle annotation is ignored in jax-rs
    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
