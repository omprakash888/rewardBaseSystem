package com.card.task2.rewardbasesystem.controller;

import com.card.task2.rewardbasesystem.payload.BankRuleDto;
import com.card.task2.rewardbasesystem.service.BankRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
