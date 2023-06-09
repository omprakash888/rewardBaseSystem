package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.BankRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BankRuleRepository extends JpaRepository<BankRule, Long> {

    @Query("select r from BankRule r WHERE ((lower(r.bankName) = lower(:bankName)) AND (lower(r.cardName) = LOWER(:cardName)) AND (r.validityOfRule >= :now) AND (r.ruleApplicable = :cardType))")
    public List<BankRule> findAllByBankNameAndCardName(String bankName, String cardName,String cardType, LocalDate now);

    @Query("select r from BankRule r WHERE ((lower(r.bankName) = lower(:bankName)) AND (lower(r.cardName) = LOWER(:cardName)) AND (r.spendCategory = 'annual expenses'))")
    public BankRule findByBankNameAndCardNameAndRule(String bankName, String cardName);
}
