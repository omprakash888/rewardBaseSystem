package com.card.task2.rewardbasesystem.controller;

import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.payload.TransactionDto;
import com.card.task2.rewardbasesystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("api/createTransaction")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto createdTransactionDto = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(createdTransactionDto, HttpStatus.CREATED);
    }

}
