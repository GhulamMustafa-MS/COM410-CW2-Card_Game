package cw2.project;

import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void scoreForSuitCalculatesCorrectly() {
        Hand hand = new Hand(); // random initially, we override the cards

        // Hearts: 2 + Jack(10) + Ace(11) = 23
        hand.replaceCard(0, new Card(0, 2));   // 2 of Hearts
        hand.replaceCard(1, new Card(9, 2));   // Jack of Hearts
        hand.replaceCard(2, new Card(12, 2));  // Ace of Hearts

        // Other suits
        hand.replaceCard(3, new Card(8, 0));   // 10 of Clubs
        hand.replaceCard(4, new Card(6, 3));   // 8 of Spades

        hand.sortHand();

        assertEquals(23, hand.getScoreForSuit("Hearts"));
        assertEquals(10, hand.getScoreForSuit("Clubs"));
        assertEquals(8, hand.getScoreForSuit("Spades"));
        assertEquals(0, hand.getScoreForSuit("Diamonds"));
    }

    @Test
    public void inHandChecksRankAndSuit() {
        Hand hand = new Hand();

        // Put known cards in
        hand.replaceCard(0, new Card(5, 1));   // 7 of Diamonds
        hand.replaceCard(1, new Card(5, 2));   // 7 of Hearts
        hand.replaceCard(2, new Card(5, 3));   // 7 of Spades
        hand.replaceCard(3, new Card(5, 0));   // 7 of Clubs
        hand.replaceCard(4, new Card(12, 3));  // Ace of Spades
        hand.sortHand();

        assertTrue(hand.inHand(new Card(12, 3)));   // Ace of Spades exists
        assertTrue(hand.inHand(new Card(5, 2)));    // 7 of Hearts exists
        assertFalse(hand.inHand(new Card(12, 2)));  // Ace of Hearts does NOT exist
    }
}
