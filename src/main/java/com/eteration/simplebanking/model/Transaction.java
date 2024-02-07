package com.eteration.simplebanking.model;


import com.eteration.simplebanking.dto.Type;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private LocalDateTime date;

    @Column(name = "amount")
    private double amount;

    @Column(name ="transaction_type" , insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type transactionType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private Account account;

    @PrePersist
    public void onCreate(){
        this.date = LocalDateTime.now();
    }

   public void apply(Account account) throws InsufficientBalanceException {
        account.addTransaction(this);
    }

    public Transaction(double amount){
       this.amount = amount;
    }
}


