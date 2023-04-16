package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.payload.CardDto;
import com.card.task2.rewardbasesystem.repository.CardRepository;
import com.card.task2.rewardbasesystem.repository.UserRepository;
import com.card.task2.rewardbasesystem.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardServiceImplementations implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardDto createCard(CardDto cardDto) {
        System.out.println("is credit card " + cardDto.isCreditCard());
        Card card = mapToCard(cardDto);
        User user = this.userRepository.findById(cardDto.getUserId()).orElseThrow();
        card.setUser(user);
        String cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
        cardNumber = cardNumber.length() < 16 ? "8" + cardNumber : cardNumber;
        card.setCardNumber(cardNumber);
        String cvv = String.valueOf((int) (Math.random() * 1000));
        cvv = cvv.length() < 3 ? "4" + cvv : cvv;
        card.setCvv(cvv);
        card.setExpiryDate(LocalDate.now().plusYears(5));
        card.setCardCreatedDate(LocalDate.now());
        Card createdCard = this.cardRepository.save(card);

        return mapToCardDto(createdCard);
    }

    private Card mapToCard(CardDto cardDto) {
        return this.modelMapper.map(cardDto,Card.class);
    }

    private CardDto mapToCardDto(Card card) {
        return this.modelMapper.map(card, CardDto.class);
    }
}
