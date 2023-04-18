package com.card.task2.rewardbasesystem.controller;

import com.card.task2.rewardbasesystem.payload.CardDto;
import com.card.task2.rewardbasesystem.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/card/")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("createCard")
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        CardDto createdCard = this.cardService.createCard(cardDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("rewardPoints")
    public ResponseEntity<String> getTotalRewardPoints(@RequestParam long cardId) {
        String totalRewardPoints = this.cardService.getTotalPoints(cardId);
        return new ResponseEntity<>(totalRewardPoints, HttpStatus.OK);
    }
}
