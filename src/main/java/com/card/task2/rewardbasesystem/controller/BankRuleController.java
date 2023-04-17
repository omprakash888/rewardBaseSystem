package com.card.task2.rewardbasesystem.controller;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.payload.BankRuleDto;
import com.card.task2.rewardbasesystem.service.BankRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bankRule/")
public class BankRuleController {

    @Autowired
    private BankRuleService bankRuleService;

    @PostMapping("createRule")
    public ResponseEntity<String> createRule(@RequestBody BankRuleDto bankRuleDto) {
        String message = this.bankRuleService.createRule(bankRuleDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("getAllRules")
    public ResponseEntity<List<BankRule>> getAllRules() {
        List<BankRule> bankRules = this.bankRuleService.getAllRules();
        return new ResponseEntity<>(bankRules, HttpStatus.OK);
    }

    @GetMapping("getRule")
    public ResponseEntity<BankRule> getRule(@RequestParam long ruleId) {
        BankRule bankRule = this.bankRuleService.getRuleById(ruleId);
        return new ResponseEntity<>(bankRule, HttpStatus.OK);
    }

    @PostMapping ("updateRule")
    public ResponseEntity<String> updateRule(@RequestBody BankRuleDto bankRuleDto) {
        this.bankRuleService.updateRule(bankRuleDto);
        return new ResponseEntity<>("rule updated Successfully", HttpStatus.ACCEPTED);
    }
}
