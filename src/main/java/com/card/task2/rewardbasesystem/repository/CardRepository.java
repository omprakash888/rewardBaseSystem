package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
}
