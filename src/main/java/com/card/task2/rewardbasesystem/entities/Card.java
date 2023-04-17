package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_details")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;

    private String cardNumber;

    private String cvv;

    private String nameOnCard;

    private boolean isCreditCard;

    private String cardName;

    private LocalDate expiryDate;

    private LocalDate cardCreatedDate;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
