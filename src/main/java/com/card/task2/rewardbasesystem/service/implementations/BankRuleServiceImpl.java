package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.payload.BankRuleDto;
import com.card.task2.rewardbasesystem.repository.BankRuleRepository;
import com.card.task2.rewardbasesystem.service.BankRuleService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        this.bankRuleRepository.save(bankRule);
        return "Rule Created Successfully";
    }


    private BankRule mapToBankRule(BankRuleDto bankRuleDto) {
        return this.modelMapper.map(bankRuleDto, BankRule.class);
    }
}
