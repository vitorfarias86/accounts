package com.pismo.accounts.control;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

public class AccountDTO {
    
	public String id;
	
	@NotEmpty(message = "{accounts.empty.id}")
    @CPF(message = "{accounts.invalid.document}")
    public String documentNumber;
    
    private AccountDTO(String id, String documentNumber) {
    	this.id = id;
		this.documentNumber = documentNumber; 
	}
    
    
    public static AccountDTO of(String id, String documentNumber) {
        return new AccountDTO(id, documentNumber);
    }
    
    public static AccountDTO withDocument(String documentNumber) {
        return new AccountDTO(null, documentNumber);
    }
}
