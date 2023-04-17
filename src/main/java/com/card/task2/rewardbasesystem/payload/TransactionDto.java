package com.card.task2.rewardbasesystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private long transactionId;

    private boolean isCreditTransaction;

    private int transactionAmount;

    private String transactionMode;

    private String spendCategory;

    private String rewardPoints;

    private String cashback;

    private long cardId;
}
