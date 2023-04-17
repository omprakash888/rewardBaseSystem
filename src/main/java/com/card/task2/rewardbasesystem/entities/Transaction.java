package com.card.task2.rewardbasesystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_details")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    private boolean isCreditTransaction;

    private int transactionAmount;

    private String transactionMode;

    private String spendCategory;

    private LocalDateTime transactionDate;

    private int rewardPoints;

    private int cashback;

    private LocalDateTime expiryDateOfRewardPoints;

    @ManyToOne
    @JsonIgnore
    private Card card;

}
