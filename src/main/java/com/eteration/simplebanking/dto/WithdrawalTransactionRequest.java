package com.eteration.simplebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalTransactionRequest {

    @DecimalMin(value = "0.1")
    private double amount;

}
