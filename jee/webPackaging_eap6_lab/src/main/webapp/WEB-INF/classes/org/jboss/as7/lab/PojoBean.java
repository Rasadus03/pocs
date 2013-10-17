package org.jboss.as7.lab;

import javax.naming.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class PojoBean {

    private static Logger log = LoggerFactory.getLogger("PojoBean");
    private @PersistenceUnit(unitName="lab_EMF")  EntityManagerFactory labEMF;

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
        InitialContext jndiContext = null;
        try {
            jndiContext = new InitialContext();
            labEMF = (EntityManagerFactory)jndiContext.lookup("java:/comp/env/lab_EMF");
        } catch(Exception x) {
            throw new RuntimeException(x);
        }finally {
            if(jndiContext != null)
                try { jndiContext.close(); } catch(Exception x) { x.printStackTrace(); }
        }
        log.info("constructor() this = "+this+"  : emf = "+labEMF);
    }


    /**
     * Useage :  curl $HOSTNAME:8080/webPackaging_eap6_lab/pojo/wavechain1
     */
    @GET
    @Path("/pojo/{pojoId}")
    @Produces("text/plain")
    public Response findPojo(@PathParam("pojoId") String pojoId) {
        log.info("findPojo() pojoId = "+pojoId);
        EntityManager emObj = labEMF.createEntityManager();
        Pojo pojoObj = emObj.find(Pojo.class, pojoId);
        ResponseBuilder builder = null;
        if(pojoObj == null) {
            builder = Response.ok("great success!");
        } else {
            builder = Response.status(Status.NOT_FOUND);
        }
        return builder.build();
    }

    /**
     *sample usage :   
     *  curl -X PUT  --data-binary "{"sanityCheck":{"youRule":"false","needToFindNewJob":"true"}}" -HContent-Type:application/json \
                               -HAccept:text/plain http://$HOSTNAME:8080/webPackaging_eap6_lab/SANITY_CHECK_8d6cf33a-3d2d-11df-bb50-077a101f1ede
     */
    @PUT @Path("/SANITY_CHECK_{uniqueId: .*}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response sanityCheck(@PathParam("uniqueId")final String uniqueId, final String jsonString) throws Exception {
        try {
            log.info("sanityCheck() uniqueId = "+uniqueId+" : jsonString = "+jsonString);
            ResponseBuilder builder = Response.ok("nice job, Jerky!\n");
            return builder.build();
        } catch(Throwable x) {
            x.printStackTrace();
            ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
            return builder.build();
        }
    }



    // NOTE:  this lifecycle annotation is ignored in jax-rs
    @PreDestroy
    public void stop() {
        log.info("stop()");
    }
}
