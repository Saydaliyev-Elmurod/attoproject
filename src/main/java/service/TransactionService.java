package service;

import container.ComponentContainer;
import dto.Card;
import dto.Profile;
import dto.Terminal;
import dto.Transaction;
import enums.CardStatus;
import enums.TerminalStatus;
import lombok.Setter;
import repository.CardRepository;
import repository.TerminalRepository;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
public class TransactionService {
    private CardRepository cardRepository;
    private TerminalRepository terminalRepository;
    private TransactionRepository transactionRepository;

    public void transaction(Profile profile, String numCard, String terminalNumber) {

        Card card = cardRepository.getCard(numCard);
        if (card == null || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("This card isn't your");
            return;
        } else if (card.getStatus().equals(CardStatus.BLOCK) || card.getStatus().equals(CardStatus.ADMIN_BLOCK)) {
            System.out.println("This card blocked");
            return;
        } else if (card.getAmount() < 1400) {
            System.out.println("Not enough money");
            return;
        }
        Terminal terminal = terminalRepository.getTerminalByNumber(terminalNumber);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        } else if (terminal.getStatus().equals(TerminalStatus.BLOCK)) {
            System.out.println("This terminal doesn't work");
            return;
        }
        card.setAmount(card.getAmount() - ComponentContainer.amountPrice);
        cardRepository.updateCardBalance(card);

        Card companyCard = cardRepository.getCard(ComponentContainer.companyCard);
        companyCard.setAmount(companyCard.getAmount() + ComponentContainer.amountPrice);
        cardRepository.updateCardBalance(companyCard);

        transactionRepository.transaction(profile, card, terminal);


    }

    public List<Transaction> paymentDay(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return transactionRepository.paymentDay(localDate);
    }

    public List<Transaction> intermediatePayment(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFrom = LocalDate.parse(fromDate, formatter);
        LocalDate dateTo = LocalDate.parse(toDate, formatter);
        return transactionRepository.intermediatePayment(dateFrom, dateTo);
    }

    public List<Transaction> transactionByTerminal(String terminalNum) {
        Terminal terminal = terminalRepository.getTerminalByNumber(terminalNum);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return null;
        }
        return transactionRepository.transactionByTerminal(terminal);

    }

    public List<Transaction> transactionByCard(String cardNum) {
        Card card = cardRepository.getCard(cardNum);
        if (card == null) {
            System.out.println("Card not found");
            return null;
        }
        return transactionRepository.transactionByCard(card);
    }
}
