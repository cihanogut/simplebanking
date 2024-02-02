package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneBillPaymentTransaction extends Transaction {
    private String provider;
    private String phoneNumber;
    private double amount;

    public PhoneBillPaymentTransaction(String provider, String phoneNumber, double amount) {
        this.provider = provider;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    @Override
    public void apply(Account account) {
        account.withdraw(amount);
    }
}
