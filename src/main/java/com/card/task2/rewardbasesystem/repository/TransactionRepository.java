package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
