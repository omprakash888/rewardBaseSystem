package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.BankRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankRuleRepository extends JpaRepository<BankRule, Long> {

    @Query("select r from BankRule r WHERE ((lower(r.bankName) = lower(?1)) AND (lower(r.cardName) = LOWER(?2)))")
    List<BankRule> findAllByBankNameAndCardName(String bankName, String cardName);
}
