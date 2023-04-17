package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {


}
