package com.card.task2.rewardbasesystem.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDto {

    private long id;
    private String userName;

    private String bankName;

    private String mobileNumber;

    private String email;


}
