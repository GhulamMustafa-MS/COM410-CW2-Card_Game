package cw2.project;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class DeckTest {

    @Test
    public void dealing52CardsGivesNoNullThenNull() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            assertNotNull("Card " + i + " should not be null", deck.deal());
        }

        assertNull("After 52 cards, deck should be empty and return null", deck.deal());
    }

    @Test
    public void deckHas52UniqueCards() {
        Deck deck = new Deck();
        Set<String> seen = new HashSet<String>();

        for (int i = 0; i < 52; i++) {
            Card c = deck.deal();
            assertNotNull(c);

            String key = c.getRank() + "-" + c.getSuit();
            assertTrue("Duplicate card dealt: " + key, seen.add(key));
        }
    }
}
