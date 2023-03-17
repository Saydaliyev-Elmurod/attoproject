package service;

import container.ComponentContainer;
import dto.Card;
import dto.Profile;
import enums.CardStatus;
import lombok.Setter;
import repository.CardRepository;
import repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;
@Setter
public class CardService {
    private CardRepository cardRepository;
    private TransactionRepository transactionRepository;

    public void createCard(String number, String exp_date) {
        Card card = cardRepository.getCard(number);
        //check
        if (card != null) {
            System.out.println("This card already exists");
            return;
        }
        //>>>>>>
       cardRepository.createCard(number, exp_date);
    }

    public void updateCardByNumber(String number, String exp_date) {
        Card card = cardRepository.getCard(number);
        if (card == null || !card.getExp_date().equals(exp_date)) {
            System.out.println("Card not found");
        } else {
            System.out.print("Enter new card number : ");
            String newCardNum = ComponentContainer.stringScanner.next();
            System.out.print("Enter new card exp_date : ");
            String new_exp_date = ComponentContainer.stringScanner.next();
            Card card1 = cardRepository.getCard(newCardNum);
            if (card1 == null || card1.getNumber().equals(number)) {
                cardRepository.updateCardByNumber(card, newCardNum, new_exp_date);
                System.out.println("Successfully");
            } else {
                System.out.println("This card already exists");
            }
        }
    }

    public void changeCardStatus(String numCard, String exp_date) {
        Card card = cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(exp_date)) {
            System.out.println("Card not found");
        } else {
            if (card.getStatus().equals(CardStatus.ADMIN_BLOCK)) {
                card.setStatus(CardStatus.ACTIVE);
            } else if (card.getStatus().equals(CardStatus.ACTIVE)) {
                card.setStatus(CardStatus.ADMIN_BLOCK);
            }
            cardRepository.updateCardStatus(card);

        }
    }

    public void deleteCard(String numCard, String expDate) {
        Card card = cardRepository.getCard(numCard);
        if (card == null || (!card.getExp_date().equals(expDate))) {
            System.out.println("Card not found");
            return;
        }
        cardRepository.deleteCard(card);
    }

    public List<Card> cardListUser(Profile profile) {
        List<Card> cardList = cardRepository.cardListUser(profile);
        return cardList.stream().filter(card -> !card.getStatus().equals(CardStatus.NOT_VISIBLE_USER)).collect(Collectors.toList());
    }

    public void addCard(Profile profile, String numCard, String cardExpDate) {
        Card card = cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate)) {
            System.out.println("Card not found");
            return;
        } else if (card.getProfile_id() != profile.getId() && card.getProfile_id() != 0) {
            System.out.println("This card belong other profile");
            return;
        }
        cardRepository.addCardToUser(profile, card);
    }

    public void changeCardStatusUser(Profile profile, String numCard, String cardExpDate) {
        Card card = cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate) || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else {
            if (card.getStatus().equals(CardStatus.BLOCK)) {
                card.setStatus(CardStatus.ACTIVE);
            } else if (card.getStatus().equals(CardStatus.ACTIVE)) {
                card.setStatus(CardStatus.BLOCK);
            }else if (card.getStatus().equals(CardStatus.ADMIN_BLOCK)){
                System.out.println("This card blocked by admin ,you cannot activated");
                return;
            }
            cardRepository.updateCardStatus(card);

        }
    }

    public void deleteCardUser(Profile profile, String numCard, String cardExpDate) {
        Card card =cardRepository.getCard(numCard);
        if (card == null || !card.getExp_date().equals(cardExpDate) || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else {
            card.setStatus(CardStatus.NOT_VISIBLE_USER);
            cardRepository.updateCardStatus(card);

        }
    }

    public void refill(Profile profile, String numCard, Double amount) {
        Card card = cardRepository.getCard(numCard);
        if (card == null || !card.getProfile_id().equals(profile.getId())) {
            System.out.println("Your Card  not found");
        } else if (card.getStatus().equals(CardStatus.BLOCK)||card.getStatus().equals(CardStatus.ADMIN_BLOCK)) {
            System.out.println("This card blocked ");
        } else {
            card.setAmount(card.getAmount() + amount);
            cardRepository.updateCardBalance(card);
            transactionRepository.refill(profile,card,amount);

        }
    }
}
