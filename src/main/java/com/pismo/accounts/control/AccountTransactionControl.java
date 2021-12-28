package com.pismo.accounts.control;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jboss.logging.Logger;

import com.pismo.accounts.entity.Account;
import com.pismo.accounts.entity.AccountTransaction;

@Dependent
public class AccountTransactionControl {

    @Inject
    Logger log;
    
    @Inject
    TransactionMaker transactionMaker;
    
	@Transactional(value = TxType.REQUIRED)
	public AccountTransaction create(AccountTransactionDTO accountTransactionDTO) {
		
		String transactionId = UUID.randomUUID().toString();
		
		Account account = retrieveAccount(accountTransactionDTO);
		
		OperationTypes operationType = OperationTypes.getOperationType(accountTransactionDTO.type);
		
		BigDecimal amount = transactionMaker.processTransaction(operationType, accountTransactionDTO.amount);
		AccountTransaction accountTransaction = AccountTransaction.of(transactionId, account, operationType.code, amount);
		
		log.infof("message: save transaction. payload: %s", accountTransaction.toString());
		AccountTransaction.persist(accountTransaction);
		
		return accountTransaction;
	}

	private Account retrieveAccount(AccountTransactionDTO accountTransactionDTO) {
		Optional<Account> account = Account.findByIdOptional(accountTransactionDTO.accountId);
		if(!account.isPresent()) {
			throw new AccountNotFoundException(String.format("Account with id %s not found!", accountTransactionDTO.accountId));
		}
		return account.get();
	}
	
}
