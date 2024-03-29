package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Getter
@Setter
@Entity
@NoArgsConstructor
public class WithdrawalTransaction extends Transaction {

    @Column(name = "approvalCode")
    private UUID approvalCode = UUID.randomUUID();

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public void apply(Account account) throws InsufficientBalanceException {
        account.withdraw(this.getAmount());
        account.getTransactions().add(this);
    }
}


