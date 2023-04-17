package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.entities.User;
import com.card.task2.rewardbasesystem.payload.TransactionDto;
import com.card.task2.rewardbasesystem.repository.BankRuleRepository;
import com.card.task2.rewardbasesystem.repository.CardRepository;
import com.card.task2.rewardbasesystem.repository.TransactionRepository;
import com.card.task2.rewardbasesystem.repository.UserRepository;
import com.card.task2.rewardbasesystem.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BankRuleRepository bankRuleRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository, UserRepository userRepository, ModelMapper modelMapper, BankRuleRepository bankRuleRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bankRuleRepository = bankRuleRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = mapToTransaction(transactionDto);
        Card card = cardRepository.findById(transactionDto.getCardId()).orElseThrow();
        User user = card.getUser();
        transaction.setCard(card);
        transaction.setTransactionDate(LocalDateTime.now());
        BankRule bankRule = getRewardRule(transaction);

        if(bankRule == null) {
            transaction.setCashback(0);
            transaction.setRewardPoints(0);
        }
        else if(bankRule.getRule().equals("percent")) {
            int percent = Integer.parseInt(bankRule.getPoints());
            int cashBack = (transactionDto.getTransactionAmount() * percent)/100;
            transaction.setCashback(cashBack);
            transaction.setRewardPoints(0);
            user.setTotalCashBack(user.getTotalCashBack() + cashBack);
        }
        else {
            int amount = Integer.parseInt(bankRule.getRule().substring(6));
            int points = transaction.getTransactionAmount()/amount;
            int totalPoints = points * Integer.parseInt(bankRule.getPoints());
            transaction.setRewardPoints(totalPoints);
            transaction.setCashback(0);
            user.setTotalRewards(user.getTotalRewards() + totalPoints);
        }

        this.userRepository.save(user);
        Transaction createdTransaction = this.transactionRepository.save(transaction);
        return mapToTransactionDto(createdTransaction);
    }

    private BankRule getRewardRule(Transaction transaction) {
        String bankName = transaction.getCard().getUser().getBankName();
        String cardName = transaction.getCard().getCardName();

        List<BankRule> rules = this.bankRuleRepository.findAllByBankNameAndCardName(bankName, cardName);
        BankRule anyExpenseRule = null;
        for (BankRule bankRule : rules) {
            if(bankRule.getSpendCategory().contains(transaction.getSpendCategory())) {
                return bankRule;
            }
            if(bankRule.getSpendCategory().contains("any")) {
                anyExpenseRule = bankRule;
            }
        }
        if(anyExpenseRule != null) {
            return anyExpenseRule;
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
