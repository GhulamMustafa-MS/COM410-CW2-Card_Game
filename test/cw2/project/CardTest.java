package cw2.project;

import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void cardToStringLooksRight() {
        Card c = new Card(12, 2); // Ace of Hearts
        assertEquals("Ace of Hearts", c.toString());
    }

    @Test
    public void compareToUsesRank() {
        Card low = new Card(0, 0);   // 2 of Clubs
        Card high = new Card(12, 3); // Ace of Spades

        assertTrue(high.compareTo(low) > 0);
        assertTrue(low.compareTo(high) < 0);

        // same rank should compare equal (your compareTo uses rank only)
        assertEquals(0, new Card(5, 0).compareTo(new Card(5, 3)));
    }

    @Test
    public void getRankValueMatchesConstructor() {
        Card c = new Card(9, 1); // Jack of Diamonds (rank index 9)
        assertEquals(9, c.getRankValue());
        assertEquals("Jack", c.getRank());
        assertEquals("Diamonds", c.getSuit());
    }
}
