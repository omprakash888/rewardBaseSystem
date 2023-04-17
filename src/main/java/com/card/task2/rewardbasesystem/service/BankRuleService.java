package com.card.task2.rewardbasesystem.service;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.payload.BankRuleDto;

import java.util.List;

public interface BankRuleService {
    public String createRule(BankRuleDto bankRuleDto);

    public List<BankRule> getAllRules();

    public void updateRule(BankRuleDto bankRuleDto);

    public BankRule getRuleById(long ruleId);
}
