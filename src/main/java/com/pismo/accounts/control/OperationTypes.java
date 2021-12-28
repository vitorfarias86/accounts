package com.pismo.accounts.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public enum OperationTypes {
    
	COMPRA_A_VISTA(1), 
	COMPRA_PARCELADA(2), 
	SAQUE(3), 
	PAGAMENTO(4);
	
	public int code;
	
	private OperationTypes(int code) {
		this.code = code;
	}
	public static OperationTypes getOperationType(int code) {
		
		for (OperationTypes operation : OperationTypes.values()) {
			if (operation.code == code) {
				return operation;
			}
		}
		throw new WebApplicationException("Invalid operation type.", Response.Status.BAD_REQUEST);
	}
	
}
