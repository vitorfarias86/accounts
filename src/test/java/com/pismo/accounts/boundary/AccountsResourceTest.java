package com.pismo.accounts.boundary;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.accounts.control.AccountDTO;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AccountsResourceTest {

	@Inject
	ObjectMapper objectMapper;
	
	@Inject
	Logger log;
	
	private final String VALID_CPF = "15416175095";
	private final String INVALID_CPF = "99999999999";
	
    @Test
    public void shouldCreateAnAccountSuccesfully() {
    	AccountDTO accountDTO = AccountDTO.withDocument(VALID_CPF);
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountDTO)).when().post("/accounts")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(200, response.statusCode());
    	 Assertions.assertEquals(VALID_CPF, response.jsonPath().getString("documentNumber"));
    }
    
    @Test
    public void shouldReturnBadRequstIfInvalidDocument() {
    	AccountDTO accountDTO = AccountDTO.withDocument(INVALID_CPF);
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountDTO)).when().post("/accounts")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(400, response.statusCode());
    	 Assertions.assertNotNull(response.jsonPath().getString("message"));
    }
    
    public String serialize(AccountDTO accountDto) {
    	try {
			return objectMapper.writeValueAsString(accountDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error trying to serialize AccountDTO ", e);
		}
    }
    
}
