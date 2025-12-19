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

    // Main method for the scenario
    public static void main(String[] args) {

        // 1. Create a hand of 5 sorted random cards
        Hand hand = new Hand();

        // 2. Output the hand
        System.out.println("Hand of 5 cards (sorted by rank): " + hand);

        // 3. Generate 10 random cards & test each for matching rank
        for (int i = 1; i <= 10; i++) {
            Card randomCard = new Card();
            String message;

            if (hand.inHand(randomCard)) {
                message = " - Positive, the card is contained in the hand";
            } else {
                message = " - Negative, no card is not contained in the hand";
            }

            System.out.println("Random card #" + i + ": " + randomCard + message);
        }
    }
}