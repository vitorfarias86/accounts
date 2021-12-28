package com.pismo.application.exception;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    Logger log;

    private final String errorMessage = "WebApplication error";

    @Override
    public Response toResponse(WebApplicationException webException) {
        log.errorf("Message: %s StackTrace: %s",
                webException.getMessage() != null ? webException.getMessage() : errorMessage,webException);

        return Response.status(webException.getResponse().getStatus())
                .header("message", webException.getMessage())
                .entity(new AccountsError(webException.getResponse().getStatus(),
                        webException.getMessage()))
                .build();
    }

 
}