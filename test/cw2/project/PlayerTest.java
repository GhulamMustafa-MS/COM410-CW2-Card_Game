package cw2.project;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class PlayerTest {

    // Predictable deck for testing swaps
    static class TestDeck extends Deck {
        private final Queue<Card> q;

        TestDeck(Card... cards) {
            super(); // build normal deck, but we ignore it
            q = new ArrayDeque<Card>(Arrays.asList(cards));
        }

        @Override
        public Card deal() {
            return q.isEmpty() ? null : q.remove();
        }
    }

    @Test
    public void playerRoundScoreAddsBonusWhenBestSuitMatches() {
        Player p = new Player("TestPlayer");

        Hand hand = new Hand(new Deck()); // ✅ FIXED (no more Hand())

        // Hearts = 2 + Jack(10) + Ace(11) = 23
        hand.replaceCard(0, new Card(0, 2));   // 2 Hearts
        hand.replaceCard(1, new Card(9, 2));   // Jack Hearts
        hand.replaceCard(2, new Card(12, 2));  // Ace Hearts

        // filler cards
        hand.replaceCard(3, new Card(1, 0));   // 3 Clubs
        hand.replaceCard(4, new Card(2, 1));   // 4 Diamonds

        hand.sortHand();

        p.setHand(hand);
        p.selectBonusSuit("Hearts");

        int score = p.calculateRoundScore();
        assertEquals(28, score); // 23 + 5 bonus
        assertEquals(28, p.getTotalScore());
    }

    @Test
    public void playerSwapReplacesSpecificIndexes() {
        Player p = new Player("TestPlayer");

        Hand hand = new Hand(new Deck()); // ✅ FIXED (no more Hand())

        // Known starting hand (all Clubs)
        hand.replaceCard(0, new Card(0, 0)); // 2 Clubs
        hand.replaceCard(1, new Card(1, 0)); // 3 Clubs
        hand.replaceCard(2, new Card(2, 0)); // 4 Clubs
        hand.replaceCard(3, new Card(3, 0)); // 5 Clubs
        hand.replaceCard(4, new Card(4, 0)); // 6 Clubs
        hand.sortHand();

        p.setHand(hand);
        p.selectBonusSuit("Clubs");

        // swap indexes 0 and 2
        TestDeck testDeck = new TestDeck(
                new Card(12, 3), // Ace of Spades
                new Card(9, 2)   // Jack of Hearts
        );

        p.swapCards(new int[]{0, 2}, testDeck);

        // confirm new cards exist in the hand
        boolean hasAceSpades = false;
        boolean hasJackHearts = false;

        for (Card c : p.getHand().getCards()) {
            if (c.toString().equals("Ace of Spades")) hasAceSpades = true;
            if (c.toString().equals("Jack of Hearts")) hasJackHearts = true;
        }

        assertTrue(hasAceSpades);
        assertTrue(hasJackHearts);
    }
}
