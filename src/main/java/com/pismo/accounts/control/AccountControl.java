package com.pismo.accounts.control;

import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jboss.logging.Logger;

import com.pismo.accounts.entity.Account;


@Dependent
public class AccountControl {

    @Inject
    Logger log;
    
    @Transactional(value = TxType.REQUIRED)
    public AccountDTO create(AccountDTO accountDTO) {
        String accountId = UUID.randomUUID().toString();
        Account account = Account.of(accountId, accountDTO.documentNumber);
        account.persist();
        
        log.infof("message: account created %s", account.toString() );
        return AccountDTO.of(account.id, account.documentNumber);
    }

    @Transactional(value = TxType.SUPPORTS)
    public Optional<AccountDTO> retrieve(String accountId) {
    	Optional<Account> optional = Account.findByIdOptional(accountId);

    	if(optional.isPresent()) {
    		Account account = optional.get();
    		log.infof("message: found account for id %s, %s", accountId, account.toString() );
    		
    		return Optional.of(AccountDTO.of(account.id, account.documentNumber));	
    	}
    	log.infof("message: account not found for id %s", accountId);
    	return Optional.empty();
    }
}
