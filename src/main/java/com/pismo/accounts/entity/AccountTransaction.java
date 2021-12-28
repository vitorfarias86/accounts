package com.pismo.accounts.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "account_transaction")
public class AccountTransaction extends PanacheEntityBase {
    
	
    public AccountTransaction(String id, Account account, int type, BigDecimal amount,
			LocalDateTime eventDate) {
		super();
		this.id = id;
		this.account = account;
		this.type = type;
		this.amount = amount;
		this.eventDate = eventDate;
	}

	public static AccountTransaction of(String id, Account account, int type, BigDecimal amount) {
        return new AccountTransaction(id, account, type, amount, LocalDateTime.now());
    }
	
    @Id
    @Column(length = 36)
    public String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "account_id_fk"))
    public Account account;

    @Column(name = "operation_type")
    public int type;

    public BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @Column(name = "event_date")
    public LocalDateTime eventDate;
    
    public AccountTransaction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{id:").append(id).append(", account:").append(account).append(", type:").append(type)
				.append(", amount:").append(amount).append(", eventDate:").append(eventDate).append("}");
		return builder.toString();
	}
    
}
