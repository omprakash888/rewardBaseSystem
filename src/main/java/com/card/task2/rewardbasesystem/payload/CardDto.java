package com.card.task2.rewardbasesystem.payload;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private long cardId;
    private String nameOnCard;

    private boolean isCreditCard;

    private String cardName;

    private long userId;

    private int totalRewards;

    private int cashBack;

}
