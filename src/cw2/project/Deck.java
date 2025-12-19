package cw2.project;

import java.util.ArrayList;
import java.util.Collections;

// Class definition
public class Deck {
    // Declaring a list to store Card objects
    private ArrayList<Card> deck = new ArrayList<Card>();

    // Constructor method to create a full deck of 52 cards
    public Deck() {
        // Adds a Card object to the deck list 52 times
        for (int i = 0; i < 52; i++) {
            // i % 13 gives the remainder; makes 0 to 12 for 4 times
            // i / 13 gives the integer division; makes 0, 1, 2, or 3
            this.deck.add(new Card(i % 13, i / 13));
        }
        // Randomise (shuffle) the order of the cards in the deck
        Collections.shuffle(this.deck);
    }

    // Regular method to deal (draw) the top card from the deck
    public Card deal() {
        if (this.deck.size() > 0) { // check if the deck is not empty
            Card card = this.deck.get(0); // Get the first card in the list
            this.deck.remove(0);          // Remove the drawn card from the deck
            return card;                  // Return the drawn card to the player
        } else return null;               // If no cards are left, return null
    }

    // Returns a string representation of all cards in the deck
    public String toString() {
        String resultStr = "\n"; // Just start with an empty string in new line
        // Iteration for all the card objects in the deck collection
        for (Card card : deck) {
            resultStr += card + "\n"; // Building a string of all cards
        }
        return resultStr; // Return the string of the full list of cards
    }
}

