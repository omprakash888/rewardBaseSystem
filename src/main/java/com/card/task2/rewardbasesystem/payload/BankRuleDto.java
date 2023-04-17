package com.card.task2.rewardbasesystem.payload;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankRuleDto {

    private long id;

    private String bankName;

    private String cardName;

    private String rule;

    private String points;

    private String spendCategory;

    private LocalDate ruleCreatedDate;

    private boolean isRuleActive;

    private LocalDate ruleUpdatedDate;
}
