package cw2.project;

import java.util.Arrays;

public class Hand {

    private Card[] cards;   // fixed size of 5

    // I changed this constructor to deal 5 random cards from 1 deck instead of 5 random cards
    // because just 5 random cards could have caused duplication which would break the game
    public Hand(Deck deck) {
        cards = new Card[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = deck.deal();
        }
        Arrays.sort(cards);
    }


    // This method checks for the suit and rank, so 2 cards of same number can be distiguished between
    public boolean inHand(Card searchCard) {
        for (int i = 0; i < 5; i++) {
            if (cards[i].getRankValue() == searchCard.getRankValue()
                    && cards[i].getSuit().equalsIgnoreCase(searchCard.getSuit())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += cards[i].toString() + " ";
        }
        return result.trim();
    }
    // This method is made for the swapping mechanism in the game
    public void replaceCard(int index, Card newCard) {
        if (index >= 0 && index < cards.length) {
            cards[index] = newCard;
        }
    }

    // This is a helper method just to sort the hand
    public void sortHand() {
        Arrays.sort(cards);
    }
    
    // This just returns the current hand
    public Card[] getCards() {
        return cards;
    }
    
    //This method is for the funcionality where we tell the user the maximum score for highest suit
    public int getScoreForSuit(String suit) {
        int total = 0;
        for (Card c : cards) {
            if (c.getSuit().equalsIgnoreCase(suit)) {
                int rankValue = c.getRankValue();
                if (rankValue >= 0 && rankValue <= 8) total += rankValue + 2; // 2–10
                else if (rankValue >= 9 && rankValue <= 11) total += 10;       // J Q K
                else if (rankValue == 12) total += 11;                         // Ace
            }
        }
        return total;
    }
}
