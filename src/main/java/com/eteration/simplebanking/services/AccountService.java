package com.eteration.simplebanking.services;


import com.eteration.simplebanking.dto.DepositTransactionRequest;
import com.eteration.simplebanking.dto.Type;
import com.eteration.simplebanking.dto.WithdrawalTransactionRequest;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


// This class is a place holder you can change the complete implementation
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account findAccount(String accountNumber) {
        Account account = get(accountNumber);
        return account;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DepositTransaction credit(String accountNumber, DepositTransactionRequest depositTransactionRequest) throws InsufficientBalanceException {

        try {
            Account account = get(accountNumber);
            DepositTransaction depositTransaction = new DepositTransaction();
            depositTransaction.setAccount(account);
            depositTransaction.setAmount(depositTransactionRequest.getAmount());
            depositTransaction.setType(Type.DepositTransaction);
            account.post(depositTransaction);
            repository.save(account);

            return depositTransaction;
        }catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public WithdrawalTransaction debit(String accountNumber, WithdrawalTransactionRequest withdrawalTransactionRequest) throws InsufficientBalanceException {
        try {
            Account account = get(accountNumber);
            WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
            withdrawalTransaction.setAccount(account);
            withdrawalTransaction.setAmount(withdrawalTransactionRequest.getAmount());
            withdrawalTransaction.setType(Type.WithdrawalTransaction);
            account.post(withdrawalTransaction);
            repository.save(account);

            return withdrawalTransaction;
        }catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }

    }

    private Account get(String accountNumber){
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account has been not found"));
    }
}
