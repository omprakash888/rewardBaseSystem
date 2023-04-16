package com.card.task2.rewardbasesystem.service;

import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.payload.UserDto;

public interface UserService {
    public UserDto createUser(UserDto userDto);
}
