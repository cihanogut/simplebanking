package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DepositTransaction extends Transaction {

    @Column(name = "approvalCode")
    private UUID approvalCode = UUID.randomUUID();

    @Column(name = "amount")
    private double amount;

    public DepositTransaction(double amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Account account) {
        account.deposit(amount);
    }

}
