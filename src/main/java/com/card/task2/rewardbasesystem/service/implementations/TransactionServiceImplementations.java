package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.RewardBaseSystemApplication;
import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.payload.TransactionDto;
import com.card.task2.rewardbasesystem.repository.CardRepository;
import com.card.task2.rewardbasesystem.repository.TransactionRepository;
import com.card.task2.rewardbasesystem.repository.UserRepository;
import com.card.task2.rewardbasesystem.service.TransactionService;
import com.card.task2.rewardbasesystem.utils.RewardRule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImplementations implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = mapToTransaction(transactionDto);
        Card card = cardRepository.findById(transactionDto.getCardId()).orElseThrow();
        User user = card.getUser();
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());
        RewardRule rewardRule = getRewardRule(transaction);
        assert rewardRule != null;
        if(rewardRule.getRule().equals("percent")) {
            int percent = Integer.parseInt(rewardRule.getPoints());
            int cashBack = (transactionDto.getTransactionAmount() * percent)/100;
            transaction.setCashback(cashBack);
            transaction.setRewardPoints(0);

        }
        else {
            int amount = Integer.parseInt(rewardRule.getRule().substring(6));
            int points = transaction.getTransactionAmount()/amount;
            int totalPoints = points * Integer.parseInt(rewardRule.getPoints());
            transaction.setRewardPoints(totalPoints);
            transaction.setCashback(0);
            user.setTotalRewards(user.getTotalRewards() + totalPoints);
        }

        this.userRepository.save(user);
        Transaction createdTransaction = this.transactionRepository.save(transaction);
        return mapToTransactionDto(createdTransaction);
    }

    private RewardRule getRewardRule(Transaction transaction) {
        Map<String, List<RewardRule>> rewardRules = RewardBaseSystemApplication.reward;
        List<RewardRule> rules = rewardRules.get(transaction.getCard().getUser().getBankName());

        for (RewardRule rewardRule : rules) {
            if(rewardRule.getSpendCategory().contains(transaction.getSpendCategory())) {
                return rewardRule;
            }
        }
        return null;
    }

    private Transaction mapToTransaction(TransactionDto transactionDto) {
        return this.modelMapper.map(transactionDto, Transaction.class);
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        return this.modelMapper.map(transaction, TransactionDto.class);
    }
}
