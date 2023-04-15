package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "transaction_details")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "transactionId_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long transactionId;

    private boolean isCreditTransaction;

    private String transactionAmount;

    private String transactionMode;

    private String transactionDoneAt;

    private LocalDate transactionDate;

    private String rewardPoints;

    private String cashback;

    @ManyToOne
    private Card card;
}
