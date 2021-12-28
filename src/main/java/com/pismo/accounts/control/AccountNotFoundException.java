package com.pismo.accounts.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AccountNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 1610576653869899637L;

	public AccountNotFoundException(String message) {
		super("AccountNotFoundException: " + message, Response.status(Status.NOT_FOUND).header("message", message).build());
	}
}
