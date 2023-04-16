package com.card.task2.rewardbasesystem.repository;

import com.card.task2.rewardbasesystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
