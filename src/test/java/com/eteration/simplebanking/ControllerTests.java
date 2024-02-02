package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.DepositTransactionRequest;
import com.eteration.simplebanking.dto.WithdrawalTransactionRequest;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/*@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc*/
@ExtendWith(SpringExtension.class)
class ControllerTests  {

    //@Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;



    @Test
    public void givenId_Credit_thenReturnJson()
            throws Exception {

        //Account account = new Account("Kerem Karaca", "17892");
        DepositTransaction depositTransaction = new DepositTransaction(1000.0);
        when(service.credit(any(),any())).thenReturn(depositTransaction);

        ResponseEntity<TransactionStatus> result = controller.credit( "17982", new DepositTransactionRequest(1000.0));
        //verify(service, times(1)).credit(accountNumberCaptor.getValue(), depositTransactionArgumentCaptor.getValue());
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        //Account account = new Account("Kerem Karaca", "17892");

        //doReturn(account).when(service).findAccount( "17892");

        DepositTransaction depositTransaction = new DepositTransaction(1000.0);
        when(service.credit(any(),any())).thenReturn(depositTransaction);

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(50.0);
        when(service.debit(any(),any())).thenReturn(withdrawalTransaction);

        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransactionRequest(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransactionRequest(50.0));


        //verify(service, times(2)).findAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        //assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
            throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");

            doReturn(account).when(service).findAccount( "17892");
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransactionRequest(1000.0));
            assertEquals("OK", result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccount("17892");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransactionRequest(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
            throws Exception {

        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}
