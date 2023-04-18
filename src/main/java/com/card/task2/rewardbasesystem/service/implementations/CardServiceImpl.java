package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.exception.ResourceNotFoundException;
import com.card.task2.rewardbasesystem.payload.CardDto;
import com.card.task2.rewardbasesystem.repository.CardRepository;
import com.card.task2.rewardbasesystem.repository.TransactionRepository;
import com.card.task2.rewardbasesystem.repository.UserRepository;
import com.card.task2.rewardbasesystem.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CardDto createCard(CardDto cardDto) {
        User user = this.userRepository.findById(cardDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User","user Id", cardDto.getUserId()));
        String cardNumber = String.valueOf((long) (Math.random() * 10000000000000000L));
        cardNumber = cardNumber.length() < 16 ? "8" + cardNumber : cardNumber;
        String cvv = String.valueOf((int) (Math.random() * 1000));
        cvv = cvv.length() < 3 ? "4" + cvv : cvv;

        Card card = Card.builder()
                        .cardNumber(cardNumber)
                        .cvv(cvv)
                        .expiryDate(LocalDate.now().plusYears(5))
                        .cardCreatedDate(LocalDate.now())
                        .cardName(cardDto.getCardName())
                        .nameOnCard(cardDto.getNameOnCard())
                        .isCreditCard(cardDto.isCreditCard())
                        .user(user)
                        .build();

        Card createdCard = this.cardRepository.save(card);

        return mapToCardDto(createdCard);
    }

    @Override
    public String getTotalPoints(long cardId) {
        int totalPoints = this.transactionRepository.getRewardPoints(cardId, LocalDateTime.now());
        List<String> expiryPoints = getExpiryPoints(cardId);
        return "You have " + totalPoints + " reward points in your wallet AND " + expiryPoints.get(0);
    }

    public List<String> getExpiryPoints(long cardId) {
        List<Object[]> expiryPoints =  this.transactionRepository.getExpiryPoints(cardId, LocalDateTime.now());
        List<String> outputString = new ArrayList<>();
        for (Object[] result : expiryPoints) {
            Long sumOfRewardPoints = (Long) result[0];
            LocalDateTime expiryDateOfRewardPoints = (LocalDateTime) result[1];
            Duration duration = Duration.between(LocalDateTime.now(), expiryDateOfRewardPoints);
            long days = duration.toDays();
            long hours = duration.toHours()%days;
            outputString.add("Your " + sumOfRewardPoints + " points from total points are expiring in " + days + "  days " + hours + " hours");
        }
        return outputString;
    }

    private CardDto mapToCardDto(Card card) {
        return this.modelMapper.map(card, CardDto.class);
    }
}
