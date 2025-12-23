package cw2.project;

import java.util.Arrays;

public class Hand {

    private Card[] cards;   // fixed size of 5

    public Hand() {
        cards = new Card[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = new Card();
        }
        Arrays.sort(cards);
    }

    // Deal 5 from a deck (prevents duplicates in a round)
    public Hand(Deck deck) {
        cards = new Card[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = deck.deal();
        }
        Arrays.sort(cards);
    }

    // NEW: sort after swaps
    public void sortHand() {
        Arrays.sort(cards);
    }

    // Fixed: check rank AND suit (your old compareTo only checked rank)
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

    public void replaceCard(int index, Card newCard) {
        if (index >= 0 && index < cards.length) {
            cards[index] = newCard;
        }
    }

    public Card[] getCards() {
        return cards;
    }

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
