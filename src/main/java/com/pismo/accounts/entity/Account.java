package com.pismo.accounts.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "account")
public class Account extends PanacheEntityBase {

    @Id
    @Column(length = 36)
    public String id;

    @Column(name = "document_number")
    public String documentNumber;
    
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> accountTransaction;

    public Account() {
		// default constructor
	}
    
    private Account(String id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public static Account of(String id, String documentNumber) {
        return new Account(id, documentNumber);
    }

    @Override
    public String toString() {
        return "{" +
            "  id='" + id + "'" +
            ", documentNumber='" + documentNumber + "'" +
            "}";
    }
}
