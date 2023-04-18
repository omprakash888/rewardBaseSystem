package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.payload.UserDto;
import com.card.task2.rewardbasesystem.repository.UserRepository;
import com.card.task2.rewardbasesystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String accountNumber = String.valueOf((long) (Math.random() * 1000000000000000L));
        accountNumber = accountNumber.length() < 15 ? "0" + accountNumber : accountNumber;

        User user = User.builder()
                        .userName(userDto.getUserName())
                        .bankName(userDto.getBankName())
                        .email(userDto.getEmail())
                        .createdDate(LocalDate.now())
                        .mobileNumber(userDto.getMobileNumber())
                        .accountNumber(accountNumber)
                        .build();

        User createdUser = userRepository.save(user);
        return mapToUserDto(createdUser);
    }

    private UserDto mapToUserDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }
}
