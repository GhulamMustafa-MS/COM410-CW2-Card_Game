package cw2.project;

import java.util.Arrays;

public class Hand {
    
    private Card[] cards;   // array of 5 card objects (sorted by ascending rank)

    // Constructor: creates 5 random cards and sorts them
    public Hand() {
        cards = new Card[5]; // fixed size of 5

        // Fill the hand with 5 random cards
        for (int i = 0; i < 5; i++) {
            cards[i] = new Card();
        }

        Arrays.sort(cards);  // Sort the 5 cards (Card implements Comparable)
    }

    public boolean inHand(Card searchCard) {
        for (int i = 0; i < 5; i++) {

            if (cards[i].compareTo(searchCard) > 0) {
                return false;  // can't be found beyond this point
            }
            // If ranks match
            if (cards[i].compareTo(searchCard) == 0) {
                return true;
            }
        }

        return false; 
    }
    // Method: String representation of the 5-card hand
    public String toString() {
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += cards[i].toString() + " ";
        }
        return result;
    }
    // This method replaces a card, this is for high suit game where we will swap cards
    public void replaceCard(int index, Card newCard) {
        if (index >= 0 && index < cards.length) {
            cards[index] = newCard;
            Arrays.sort(cards); // Keeps the hand sorted
        }
    }

    // This method gives us all the cards
    public Card[] getCards() {
        return cards;
    }

    // This gives us the total score for a suit
    public int getScoreForSuit(String suit) {
        int total = 0;
        for (Card c : cards) {
            if (c.getSuit().equalsIgnoreCase(suit)) {
                // Convert numeric rank to game points
                int rankValue = c.getRankValue();
                if (rankValue >= 0 && rankValue <= 8) total += rankValue + 2; // 2–10
                else if (rankValue >= 9 && rankValue <= 11) total += 10;       // Jack, Queen, King
                else if (rankValue == 12) total += 11;                        // Ace
            }
        }
        return total;
    }
}