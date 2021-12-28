package com.pismo.accounts.control;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountTransactionDTO {
	 
	@NotEmpty
	@JsonProperty("account_id")
	public String accountId;

	@Min(value = 1)
	@Max(value = 4)
	@JsonProperty("operation_type_id")
    public int type;

	@Positive
    public BigDecimal amount;
        

    public AccountTransactionDTO(String accountId, int type, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.type = type;
		this.amount = amount;
	}

	public static AccountTransactionDTO of(String accountId, int type, BigDecimal amount) {
        return new AccountTransactionDTO(accountId, type, amount);
    }
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{ account:").append(accountId).append(", type:").append(type)
				.append(", amount:").append(amount).append("}");
		return builder.toString();
	}
}
