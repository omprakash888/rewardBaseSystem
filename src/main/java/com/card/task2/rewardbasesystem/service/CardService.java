package com.card.task2.rewardbasesystem.service;

import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.payload.CardDto;

public interface CardService {
    CardDto createCard(CardDto cardDto);


    public String getTotalPoints(long cardId);
}
