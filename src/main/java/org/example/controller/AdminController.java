package org.example.controller;

import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;

import java.util.List;
@Setter
@Controller

public class AdminController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private CardService cardService;
    @Autowired
    private TerminalRepository terminalRepository;

    public void start(Profile profile) {
        boolean admin = true;
        while (admin) {
            adminMenu();
            int action = ComponentContainer.getAction();
            switch (action) {
                case 1 -> {
                    boolean b = true;
                    while (b) {
                        cardMenu();
                        int cardAction = ComponentContainer.getAction();
                        switch (cardAction) {
                            case 1 -> {
                                createCard();
                            }
                            case 2 -> {
                                cardList();
                            }
                            case 3 -> {
                                updateCard();
                            }
                            case 4 -> {
                                changeCardStatus();
                            }
                            case 5 -> {
                                deleteCard();
                            }
                            case 0 -> {
                                b = false;
                            }
                        }

                    }
                }
                case 2 -> {
                    boolean b = true;
                    while (b) {
                        terminalMenu();
                        int cardAction = ComponentContainer.getAction();
                        switch (cardAction) {
                            case 1 -> {
                                createTerminal();
                            }
                            case 2 -> {
                                terminalList();
                            }
                            case 3 -> {
                                updateTerminal();
                            }
                            case 4 -> {
                                changeTerminalStatus();
                            }
                            case 5 -> {
                                deleteTerminal();
                            }
                            case 0 -> {
                                b = false;
                            }
                        }
                    }

                }
                case 3 -> {
                    boolean b = true;
                    while (b) {
                        profileMenu();
                        int profileAction = ComponentContainer.getAction();
                        switch (profileAction) {
                            case 1 -> {
                                profileList();
                            }
                            case 2 -> {
                                changeProfileStatus();
                            }
                            case 0 -> {
                                b = false;
                            }
                        }
                    }

                }
                case 4 -> {
                    boolean b = true;
                    while (b) {
                        transactionMenu();
                        int transactionAction = ComponentContainer.getAction();
                        switch (transactionAction) {
                            case 1 -> {
                                transactionListAllProfile();
                            }
                            case 2 -> {
                                companyCardBalance();
                            }
                            case 0 -> {
                                b = false;
                            }
                        }
                    }
                }
                case 5 -> {
                    boolean b = true;
                    while (b) {
                        statisticMenu();
                        int statisticAction = ComponentContainer.getAction();
                        switch (statisticAction) {
                            case 1 -> {
                                paymentCurrentDay();
                            }
                            case 2 -> {
                                paymentDay();
                            }
                            case 3 -> {
                                intermediatePayment();
                            }
                            case 4 -> {
                                transactionByTerminal();
                            }
                            case 5 -> {
                                transactionByCard();
                            }
                            case 0 -> {
                                b = false;
                            }
                        }
                    }
                }
                case 0 -> {
                    admin = false;
                }

            }
        }

    }

    private void transactionByCard() {
        System.out.print("Enter Terminal number: ");
        String cardNum = ComponentContainer.stringScanner.next();
        List<Transaction> transactionList = transactionService.transactionByCard(cardNum);
       if (!transactionList.isEmpty()){
           print(transactionList);
       }

    }

    private void transactionByTerminal() {
        System.out.print("Enter Terminal number: ");
        String terminalNum = ComponentContainer.stringScanner.next();
        List<Transaction> transactionList = transactionService.transactionByTerminal(terminalNum);
        if (!transactionList.isEmpty()){
            print(transactionList);
        }
    }

    private void print(List<?> list) {
        if (list == null || list.size() == 0) {
            System.out.println("Noting not found");
            return;
        }
        list.stream().forEach(item -> System.out.println(item));


    }

    private void intermediatePayment() {
        System.out.print("Enter From Date (yyyy-MM-dd : ");
        String fromDate = ComponentContainer.stringScanner.next();
        System.out.print("Enter To  Date (yyyy-MM-dd : ");
        String toDate = ComponentContainer.stringScanner.next();
        List<Transaction> transactionList = transactionService.intermediatePayment(fromDate, toDate);
        print(transactionList);
    }

    private void paymentDay() {
        System.out.print("Enter Date (yyyy-MM-dd : ");
        String date = ComponentContainer.stringScanner.next();
        List<Transaction> transactionList = transactionService.paymentDay(date);
        print(transactionList);
    }

    private void paymentCurrentDay() {
        List<Transaction> transactionList = transactionRepository.paymentCurrentDay();
        print(transactionList);
    }

    private void statisticMenu() {
        System.out.println("1.Bugungi to'lovlar\n" +
                "2.Kunlik to'lovlar:\n" +
                "3.Oraliq to'lovlar:\n" +
                "4.Transaction by Terminal:\n" +
                "5.Transaction By Card:\n" +
                "0.Exit");
    }

    private void companyCardBalance() {
        System.out.println(cardRepository.getCard(ComponentContainer.companyCard).getAmount());
    }

    private void transactionListAllProfile() {
        List<Transaction> transactionList =transactionRepository.transactionListAllProfile();
        print(transactionList);
    }

    private void transactionMenu() {
        System.out.println("1.Transaction List\n" +
                "2.Company Card Balance\n" +
                "0.Exit");
    }

    private void changeProfileStatus() {
        System.out.print("Enter phone profile  : ");
        String phone = ComponentContainer.stringScanner.next();
        profileService.changeProfileStatus(phone);
    }

    private void profileList() {
        List<Profile> profileList =profileService.profileList();
        print(profileList);
    }

    private void profileMenu() {
        System.out.println("1.Profile List\n" +
                "2.Change Profile Status\n" +
                "0.Exit\n");
    }

    private void deleteTerminal() {
        System.out.print("Enter TERMINAL number :");
        String numTerminal = ComponentContainer.stringScanner.next();
        System.out.print("Enter address : ");
        String address = ComponentContainer.stringScanner.next();
        terminalService.deleteTerminal(numTerminal, address);

    }

    private void changeTerminalStatus() {
        System.out.print("Enter number terminal : ");
        String numTerminal = ComponentContainer.stringScanner.next();
        System.out.print("Enter address : ");
        String address = ComponentContainer.stringScanner.next();
        terminalService.changeTerminalStatus(numTerminal, address);
    }

    private void updateTerminal() {
        System.out.print("Enter terminal number : ");
        String number = ComponentContainer.stringScanner.next();
        System.out.print("Enter terminal address  : ");
        String address = ComponentContainer.stringScanner.next();
        terminalService.updateTerminalByNumber(number, address);
    }

    private void terminalList() {
        List<Terminal> cardList = terminalRepository.terminalList();
        print(cardList);
    }

    private void createTerminal() {
        System.out.print("Enter Terminal number : ");
        String number = ComponentContainer.stringScanner.next();
        System.out.print("Enter Terminal address : ");
        String address = ComponentContainer.stringScanner.next();
        terminalService.createTerminal(number, address);

    }

    private void terminalMenu() {
        System.out.println("1. Create Terminal \n" +
                "2. Terminal List\n" +
                "3. Update Terminal \n" +
                "4. Change Terminal Status\n" +
                "5. Delete\n" +
                "0. Exit\n");
    }

    private void deleteCard() {
        System.out.print("Enter number card : ");
        String numCard = ComponentContainer.stringScanner.next();
        System.out.print("Enter exp_date  : ");
        String exp_date = ComponentContainer.stringScanner.next();
        cardService.deleteCard(numCard, exp_date);
    }

    private void changeCardStatus() {
        System.out.print("Enter number card");
        String numCard = ComponentContainer.stringScanner.next();
        System.out.print("Enter exp_date  : ");
        String exp_date = ComponentContainer.stringScanner.next();
        cardService.changeCardStatus(numCard, exp_date);

    }

    private void updateCard() {
        System.out.print("Enter card number : ");
        String number = ComponentContainer.stringScanner.next();
        System.out.print("Enter exp_date  : ");
        String exp_date = ComponentContainer.stringScanner.next();
        cardService.updateCardByNumber(number, exp_date);

    }

    private void cardList() {
        List<Card> cardList =cardRepository.cardList();
        print(cardList);
    }

    private void createCard() {
        System.out.print("Enter Card number : ");
        String number = ComponentContainer.stringScanner.next();
        System.out.print("Enter exp_date : ");
        String exp_date = ComponentContainer.stringScanner.next();
        cardService.createCard(number, exp_date);
    }

    private void cardMenu() {
        System.out.println("1. Create Card\n" +
                "2. Card List\n" +
                "3. Update Card \n" +
                "4. Change Card status\n" +
                "5. Delete Card\n" +
                "0.Exit\n");
    }

    private void adminMenu() {
        System.out.println("1.Card \n" +
                "2.Terminal\n" +
                "3.Profile\n" +
                "4.Transaction\n" +
                "5.Statistic\n" +
                "0.Exit\n");

    }
}
