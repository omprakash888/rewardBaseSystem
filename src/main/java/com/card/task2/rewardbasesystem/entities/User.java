package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long userId;

    private String userName;

    private String bankName;
    private String accountNumber;

    private String mobileNumber;

    private String email;

    @Column(columnDefinition = "Integer default '0'")
    private int totalRewards;

    @Column(columnDefinition = "Integer default '0'")
    private int totalCashBack;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;


}
