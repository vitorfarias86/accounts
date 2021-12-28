package com.pismo.application.exception;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

	@Inject
	Logger log;

	private String defaultErrorMessage = "Internal application error";
	
	@Override
	public Response toResponse(Exception exception) {

		String exceptionMessage = exception.getMessage() != null ? exception.getMessage() : defaultErrorMessage;
		log.error(exceptionMessage);

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("message", exception.getMessage())
				.entity(new AccountsError(HttpURLConnection.HTTP_INTERNAL_ERROR,
						exception.getMessage() != null ? exception.getMessage() : exceptionMessage))
				.build();
	}

}