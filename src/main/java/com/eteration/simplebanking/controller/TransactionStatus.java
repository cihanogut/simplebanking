package com.eteration.simplebanking.controller;


// This class is a place holder you can change the complete implementation

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Builder
@Getter
@Setter
public class TransactionStatus {

    private String status;
    private UUID approvalCode;
}
