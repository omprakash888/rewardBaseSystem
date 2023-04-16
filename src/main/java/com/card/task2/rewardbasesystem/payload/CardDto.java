package com.card.task2.rewardbasesystem.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CardDto {

    private long cardId;
    private String nameOnCard;

    private boolean isCreditCard;

    private String cardName;

    private long userId;

}
