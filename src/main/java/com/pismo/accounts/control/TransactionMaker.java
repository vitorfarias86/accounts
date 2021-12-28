package com.pismo.accounts.control;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionMaker {

    Map<OperationTypes, Function<BigDecimal, BigDecimal>> consumers;

    private final Map<OperationTypes, Function<BigDecimal, BigDecimal>> transactionMapper = Map.of(
    		OperationTypes.COMPRA_A_VISTA,   amount -> amount.negate(),
    		OperationTypes.COMPRA_PARCELADA, amount -> amount.negate(),
    		OperationTypes.SAQUE,            amount ->  amount.negate(),
    		OperationTypes.PAGAMENTO,        amount ->  amount.abs());
    
    
    public BigDecimal processTransaction(OperationTypes type,  BigDecimal amount) {
        
    	return transactionMapper.get(type).apply(amount);
    }
}
