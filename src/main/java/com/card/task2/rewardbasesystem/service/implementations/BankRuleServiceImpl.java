package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.payload.BankRuleDto;
import com.card.task2.rewardbasesystem.repository.BankRuleRepository;
import com.card.task2.rewardbasesystem.service.BankRuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BankRuleServiceImpl implements BankRuleService {

    @Autowired
    private BankRuleRepository bankRuleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createRule(BankRuleDto bankRuleDto) {
        BankRule bankRule = mapToBankRule(bankRuleDto);
        bankRule.setRuleCreatedDate(LocalDate.now());
        bankRule.setValidityOfRule(LocalDate.now().plusYears(bankRuleDto.getValidityOfRule()));
        this.bankRuleRepository.save(bankRule);
        return "Rule Created Successfully";
    }

    @Override
    public List<BankRule> getAllRules() {
        return this.bankRuleRepository.findAll();
    }

    @Override
    public void updateRule(BankRuleDto bankRuleDto) {
        bankRuleDto.setRuleUpdatedDate(LocalDate.now());
        BankRule bankRule = mapToBankRule(bankRuleDto);
        bankRule.setValidityOfRule(LocalDate.now().plusYears(bankRuleDto.getValidityOfRule()));
        this.bankRuleRepository.save(bankRule);
    }

    @Override
    public BankRule getRuleById(long ruleId) {
        return this.bankRuleRepository.findById(ruleId).orElseThrow();
    }

    private BankRule mapToBankRule(BankRuleDto bankRuleDto) {
        return this.modelMapper.map(bankRuleDto, BankRule.class);
    }
}
