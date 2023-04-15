package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "card_details")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "cardId_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long cardId;

    private String bankName;
    private String cardNumber;

    private String cvv;

    private String nameOnCard;

    private boolean isCreditCard;

    private String nameOfCreditCard;

    private LocalDate expiryDate;

    private LocalDate cardCreatedDate;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
