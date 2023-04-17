package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String bankName;

    private String cardName;

    private String rule;

    private String points;

    private String spendCategory;

    private LocalDate ruleCreatedDate;

    private boolean isRuleActive;

    @Column(columnDefinition = "Integer default '90'")
    private int validityOfRewardPoints;

    private LocalDate validityOfRule;

    private LocalDate ruleUpdatedDate;
}
