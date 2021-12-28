package com.pismo.accounts.boundary;

import static io.restassured.RestAssured.given;

import java.math.BigDecimal;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.accounts.control.AccountTransactionDTO;
import com.pismo.accounts.control.OperationTypes;
import com.pismo.accounts.entity.Account;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AccountTransactionResourceTest {

	@Inject
	ObjectMapper objectMapper;
	
	@Inject
	Logger log;
	
	private final static String VALID_CPF = "15416175095";
	
	private final static String ACCOUNT_ID = UUID.randomUUID().toString();
	
	private final static int INVALID_MIN_OPERATION_TYPE = 0;
	private final static int INVALID_MAX_OPERATION_TYPE = 5;
	
	@BeforeAll
	@Transactional(value = TxType.REQUIRED)
	public static void init() {
		
		Account account = Account.of(ACCOUNT_ID, VALID_CPF);
		account.persist();
		
	}
	
    @Test
    public void shouldCreateATransactionPagamentoSuccesfully() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, OperationTypes.PAGAMENTO.code, BigDecimal.valueOf(123.45));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(200, response.statusCode());
    	 Assertions.assertEquals(ACCOUNT_ID, response.jsonPath().getString("account.id"));
    	 Assertions.assertEquals(OperationTypes.PAGAMENTO.code, response.jsonPath().getInt("type"));
    	 Assertions.assertTrue(BigDecimal.valueOf(123.45).equals(BigDecimal.valueOf(response.jsonPath().getDouble("amount"))));
    }
    
    @Test
    public void shouldCreateATransactionCompraAVistaSuccesfully() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, OperationTypes.COMPRA_A_VISTA.code, BigDecimal.valueOf(123.45));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(200, response.statusCode());
    	 Assertions.assertEquals(ACCOUNT_ID, response.jsonPath().getString("account.id"));
    	 Assertions.assertEquals(OperationTypes.COMPRA_A_VISTA.code, response.jsonPath().getInt("type"));
    	 Assertions.assertTrue(BigDecimal.valueOf(123.45).negate().equals(BigDecimal.valueOf(response.jsonPath().getDouble("amount"))));
    }
    
    @Test
    public void shouldCreateATransactionCompraAParceladaSuccesfully() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, OperationTypes.COMPRA_PARCELADA.code, BigDecimal.valueOf(123.45));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(200, response.statusCode());
    	 Assertions.assertEquals(ACCOUNT_ID, response.jsonPath().getString("account.id"));
    	 Assertions.assertEquals(OperationTypes.COMPRA_PARCELADA.code, response.jsonPath().getInt("type"));
    	 Assertions.assertTrue(BigDecimal.valueOf(123.45).negate().equals(BigDecimal.valueOf(response.jsonPath().getDouble("amount"))));
    }
    
    @Test
    public void shouldCreateATransactionSaqueSuccesfully() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, OperationTypes.SAQUE.code, BigDecimal.valueOf(123.45));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(200, response.statusCode());
    	 Assertions.assertEquals(ACCOUNT_ID, response.jsonPath().getString("account.id"));
    	 Assertions.assertEquals(OperationTypes.SAQUE.code, response.jsonPath().getInt("type"));
    	 Assertions.assertTrue(BigDecimal.valueOf(123.45).negate().equals(BigDecimal.valueOf(response.jsonPath().getDouble("amount"))));
    }
    
    @Test
    public void shouldReturnBadRequestOperationTypeIsOutsideOfMinimumRange() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, INVALID_MIN_OPERATION_TYPE, BigDecimal.valueOf(123));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(400, response.statusCode());
    }
    
    @Test
    public void shouldReturnBadRequestOperationTypeIsOutsideOfMaximumRange() {
    	AccountTransactionDTO accountTransactionDTO = AccountTransactionDTO.of(ACCOUNT_ID, INVALID_MAX_OPERATION_TYPE, BigDecimal.valueOf(123));
    	
    	Response response = given().header("Content-type", "application/json").and().body(serialize(accountTransactionDTO)).when().post("/transactions")
                            .then().extract().response();
    	
    	 Assertions.assertEquals(400, response.statusCode());
    }
	
    public String serialize(AccountTransactionDTO accountTransactionDTO) {
    	try {
			return objectMapper.writeValueAsString(accountTransactionDTO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error trying to serialize AccountTransactionDTO ", e);
		}
    }
	
}