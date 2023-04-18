package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.payload.BankRuleDto;
import com.card.task2.rewardbasesystem.repository.BankRuleRepository;
import com.card.task2.rewardbasesystem.service.BankRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BankRuleServiceImpl implements BankRuleService {

    @Autowired
    private BankRuleRepository bankRuleRepository;

    @Override
    public String createRule(BankRuleDto bankRuleDto) {

        BankRule bankRule = BankRule.builder()
                .ruleCreatedDate(LocalDate.now())
                .validityOfRule(bankRuleDto.getValidityOfRule() != 0 ? LocalDate.now().plusYears(bankRuleDto.getValidityOfRule()) : LocalDate.now().plusYears(100))
                .bankName(bankRuleDto.getBankName())
                .cardName(bankRuleDto.getCardName())
                .rule(bankRuleDto.getRule())
                .isRuleActive(bankRuleDto.isRuleActive())
                .ruleApplicable(bankRuleDto.getRuleApplicable())
                .points(bankRuleDto.getPoints())
                .spendCategory(bankRuleDto.getSpendCategory())
                .validityOfRewardPoints(bankRuleDto.getValidityOfRewardPoints() != 0 ? bankRuleDto.getValidityOfRewardPoints() : 3650)
                .build();
        this.bankRuleRepository.save(bankRule);
        return "Rule Created Successfully";
    }

    @Override
    public List<BankRule> getAllRules() {
        return this.bankRuleRepository.findAll();
    }

    @Override
    public void updateRule(BankRuleDto bankRuleDto) {
        BankRule bankRule = BankRule.builder()
                .id(bankRuleDto.getId())
                .ruleCreatedDate(LocalDate.now())
                .validityOfRule(bankRuleDto.getValidityOfRule() != 0 ? LocalDate.now().plusYears(bankRuleDto.getValidityOfRule()) : LocalDate.now().plusYears(100))
                .bankName(bankRuleDto.getBankName())
                .cardName(bankRuleDto.getCardName())
                .rule(bankRuleDto.getRule())
                .isRuleActive(bankRuleDto.isRuleActive())
                .points(bankRuleDto.getPoints())
                .ruleApplicable(bankRuleDto.getRuleApplicable())
                .spendCategory(bankRuleDto.getSpendCategory())
                .validityOfRewardPoints(bankRuleDto.getValidityOfRewardPoints() != 0 ? bankRuleDto.getValidityOfRewardPoints() : 3650)
                .ruleUpdatedDate(LocalDate.now())
                .build();
        this.bankRuleRepository.save(bankRule);
    }

    @Override
    public BankRule getRuleById(long ruleId) {
        return this.bankRuleRepository.findById(ruleId).orElseThrow();
    }

}
