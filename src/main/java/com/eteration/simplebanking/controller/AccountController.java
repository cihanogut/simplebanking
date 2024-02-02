package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.DepositTransactionRequest;
import com.eteration.simplebanking.dto.WithdrawalTransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


// This class is a place holder you can change the complete implementation
@RequestMapping("/account/v1")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findAccount(accountNumber));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable @NotNull @NotEmpty String accountNumber,@Valid @RequestBody DepositTransactionRequest depositTransactionRequest) throws Exception {
        DepositTransaction depositTransaction = accountService.credit(accountNumber, depositTransactionRequest);
        return ResponseEntity.ok(TransactionStatus.builder().status("OK").approvalCode(depositTransaction.getApprovalCode()).build());
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber,@Valid @RequestBody WithdrawalTransactionRequest withdrawalTransactionRequest) throws Exception {
        WithdrawalTransaction withdrawalTransaction = accountService.debit(accountNumber,withdrawalTransactionRequest);
        return ResponseEntity.ok(TransactionStatus.builder().status("OK").approvalCode(withdrawalTransaction.getApprovalCode()).build());
    }
}