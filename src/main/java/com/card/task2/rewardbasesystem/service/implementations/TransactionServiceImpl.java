package com.card.task2.rewardbasesystem.service.implementations;

import com.card.task2.rewardbasesystem.entities.BankRule;
import com.card.task2.rewardbasesystem.entities.Card;
import com.card.task2.rewardbasesystem.entities.Transaction;
import com.card.task2.rewardbasesystem.exception.ResourceNotFoundException;
import com.card.task2.rewardbasesystem.payload.TransactionDto;
import com.card.task2.rewardbasesystem.repository.BankRuleRepository;
import com.card.task2.rewardbasesystem.repository.CardRepository;
import com.card.task2.rewardbasesystem.repository.TransactionRepository;
import com.card.task2.rewardbasesystem.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BankRuleRepository bankRuleRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository, ModelMapper modelMapper, BankRuleRepository bankRuleRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
        this.bankRuleRepository = bankRuleRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Card card = cardRepository.findById(transactionDto.getCardId()).orElseThrow(() -> new ResourceNotFoundException("Card","card id",transactionDto.getCardId()));

        BankRule bankRule = getRewardRule(transactionDto, card);
        int cashBack;
        int rewardPoints;
        String rewardType = "expenses";

        if(bankRule == null || transactionDto.isCreditTransaction()) {
            cashBack = 0;
            rewardPoints = 0;
        }
        else if(bankRule.getRule().equals("percent")) {
            int percent = Integer.parseInt(bankRule.getPoints());
            cashBack = (transactionDto.getTransactionAmount() * percent)/100;
            rewardPoints = 0;
        }
        else {
            int amount = Integer.parseInt(bankRule.getRule().substring(6));
            int points = transactionDto.getTransactionAmount()/amount;
            rewardPoints = points * Integer.parseInt(bankRule.getPoints());
            cashBack = 0;
        }

        BankRule annualSpendRule = this.bankRuleRepository.findByBankNameAndCardNameAndRule(card.getUser().getBankName(), card.getCardName());
        if(annualSpendRule != null) {
            LocalDateTime lastAnnualRewardDate = this.transactionRepository.getLastAnnualRewardDate(card.getCardId(),"annual reward");
            lastAnnualRewardDate = lastAnnualRewardDate == null ? card.getCardCreatedDate().atStartOfDay() : lastAnnualRewardDate;
            if(lastAnnualRewardDate.plusYears(1).get(ChronoField.MONTH_OF_YEAR) >= LocalDateTime.now().get(ChronoField.MONTH_OF_YEAR) && lastAnnualRewardDate.plusYears(1).get(ChronoField.DAY_OF_MONTH) >= LocalDateTime.now().get(ChronoField.DAY_OF_MONTH)) {
                int totalSpendsInAYear = this.transactionRepository.getTotalSpendById(card.getCardId(), false, lastAnnualRewardDate, lastAnnualRewardDate.plusYears(1));
                rewardPoints = totalSpendsInAYear + transactionDto.getTransactionAmount() >= Integer.parseInt(annualSpendRule.getRule().substring(14)) ? rewardPoints + Integer.parseInt(annualSpendRule.getPoints()) : rewardPoints;
                rewardType = "annual reward";
            }
        }
        Transaction transaction = Transaction.builder()
                .card(card)
                .transactionDate(LocalDateTime.now())
                .cashback(cashBack)
                .rewardPoints(rewardPoints)
                .rewardType(rewardType)
                .transactionAmount(transactionDto.getTransactionAmount())
                .isCreditTransaction(transactionDto.isCreditTransaction())
                .transactionMode(transactionDto.getTransactionMode())
                .expiryDateOfRewardPoints(LocalDateTime.now().plusDays(bankRule != null ? bankRule.getValidityOfRewardPoints() : 0))
                .spendCategory(transactionDto.getSpendCategory())
                .build();

        Transaction createdTransaction = this.transactionRepository.save(transaction);
        return mapToTransactionDto(createdTransaction);
    }

    private BankRule getRewardRule(TransactionDto transactionDto, Card card) {
        String bankName = card.getUser().getBankName();
        String cardName = card.getCardName();
        String cardType = card.isCreditCard() ? "credit" : "debit";
        List<BankRule> rules = this.bankRuleRepository.findAllByBankNameAndCardName(bankName, cardName, cardType, LocalDate.now());

        return rules.stream()
                .filter(bankRule -> bankRule.getSpendCategory().contains(transactionDto.getSpendCategory()) && bankRule.isRuleActive())
                .findFirst()
                .orElseGet(() -> rules.stream()
                        .filter(bankRule -> bankRule.getSpendCategory().contains("any") && bankRule.isRuleActive())
                        .findFirst()
                        .orElse(null));
    }

    private TransactionDto mapToTransactionDto(Transaction transaction) {
        return this.modelMapper.map(transaction, TransactionDto.class);
    }
}
