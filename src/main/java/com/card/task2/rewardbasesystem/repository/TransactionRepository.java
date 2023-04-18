package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.rewardPoints), t.expiryDateOfRewardPoints FROM Transaction t join t.card c WHERE c.id = :cardId AND t.rewardPoints > 0 AND t.expiryDateOfRewardPoints = (SELECT MIN(t2.expiryDateOfRewardPoints) FROM Transaction t2 WHERE (t2.card = c AND t2.rewardPoints > 0 AND t2.expiryDateOfRewardPoints >= :now)) GROUP BY t.expiryDateOfRewardPoints")
    public List<Object[]> getExpiryPoints(long cardId, LocalDateTime now);

    @Query("SELECT SUM(t.rewardPoints) FROM Transaction t join t.card c WHERE c.id = :cardId AND t.rewardPoints > 0 AND t.expiryDateOfRewardPoints >= :now")
    public int getRewardPoints(long cardId, LocalDateTime now);

    @Query("SELECT SUM(t.transactionAmount) FROM Transaction t join t.card c WHERE c.id = :cardId AND t.isCreditTransaction = :b AND (t.transactionDate >= :startDate AND t.transactionDate < :endDate)")
    public int getTotalSpendById(long cardId, boolean b, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t.transactionDate FROM Transaction t join t.card c WHERE c.id = :cardId AND t.rewardType = :annualReward ORDER BY t.transactionDate DESC LIMIT 1")
    LocalDateTime getLastAnnualRewardDate(long cardId, String annualReward);
}
