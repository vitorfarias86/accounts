package com.pismo.application.exception;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import java.net.HttpURLConnection;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

@Inject
Logger log;

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        String errorMessage = prepareMessage(exception);
        log.error(errorMessage);

        return Response.status(Response.Status.BAD_REQUEST)
                .header("message", errorMessage)
                .entity(new AccountsError(HttpURLConnection.HTTP_BAD_REQUEST,
                        errorMessage))
                .build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        String message = "Input validation error: ";
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            message+=String.format("%s %s ", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
        }
        return message;
    }
}