package com.pismo.accounts.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import com.pismo.accounts.control.AccountTransactionControl;
import com.pismo.accounts.control.AccountTransactionDTO;
import com.pismo.accounts.entity.AccountTransaction;

@RequestScoped
@Path("transactions")
public class AccountTransactionResource {

    @Inject
    Logger log;
    
	@Inject
	AccountTransactionControl accountTransactionControl;
	
    @POST
    @Operation(summary = "Create a customer transaction")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "400", description = "If some validation error")
    @APIResponse(responseCode = "404", description = "If account not not found")
    public Response create(@Valid AccountTransactionDTO accountTransactionDTO) {
        log.infof("message: create account called payload %s", accountTransactionDTO.toString() );
        
        AccountTransaction accountTransaction = accountTransactionControl.create(accountTransactionDTO);
        return Response.ok(accountTransaction).build();
    } 
}
