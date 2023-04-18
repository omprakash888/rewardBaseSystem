package com.card.task2.rewardbasesystem.service;

import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.payload.TransactionDto;

import java.util.List;

public interface TransactionService {
    public TransactionDto createTransaction(TransactionDto transaction);

}
