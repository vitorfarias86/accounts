package com.pismo.accounts.boundary;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import com.pismo.accounts.control.AccountControl;
import com.pismo.accounts.control.AccountDTO;

@RequestScoped
@Path("accounts")
public class AccountsResource {

    @Inject
    Logger log;

    @Inject
    AccountControl accountControl;
    
    @POST
    @Operation(summary = "Create an account", description = "The account movements")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "400", description = "If some validation error occurs")
    public Response create(@Valid AccountDTO accountDTO) {
        log.infof("message: create account called payload %s", accountDTO.toString() );
        AccountDTO created = accountControl.create(accountDTO);
        return Response.ok(created).build();
    } 

    @GET
    @Path("{accountId}")
    @Operation(summary = "Retrieve an account by accountId")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "204", description = "Account id not found")
    public Response retrieve(@PathParam("accountId") String accountId) {
 
        log.infof("message: retrieve account called payload %s", accountId );
        
        Optional<AccountDTO> accountDTO = accountControl.retrieve(accountId);

        return accountDTO.isPresent() ? Response.ok(accountDTO).build() : Response.noContent().build();
    }
}