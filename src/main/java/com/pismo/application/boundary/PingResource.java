package com.pismo.application.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/health")
public class PingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int hello() {
    	return Response.ok().build().getStatus();
    }
}