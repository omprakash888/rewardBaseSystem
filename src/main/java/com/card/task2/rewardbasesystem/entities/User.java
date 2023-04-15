package com.card.task2.rewardbasesystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "userId_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private  long userId;

    private String userName;

    private String accountNumber;

    private String mobileNumber;

    private String email;

    private String totalRewards;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;


}
