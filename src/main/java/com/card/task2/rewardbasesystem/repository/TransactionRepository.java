package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t join t.card c WHERE c.id = :cardId AND t.rewardPoints > 0 ORDER BY t.expiryDateOfRewardPoints LIMIT 1")
    public Transaction getExpiryPoints(long cardId);
}
