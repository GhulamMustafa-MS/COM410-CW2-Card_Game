package cw2.project;

import java.util.ArrayList;

public class ComputerLogic {

    private Player computer;
    private Deck deck;

    public ComputerLogic(Player computer, Deck deck) {
        this.computer = computer;
        this.deck = deck;
    }

    // Choose the bonus suit (the suit with highest current score)
    public void chooseBonusSuit() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int maxScore = 0;
        String bestSuit = suits[0];

        for (String suit : suits) {
            int score = computer.getHand().getScoreForSuit(suit);
            if (score > maxScore) {
                maxScore = score;
                bestSuit = suit;
            }
        }

        computer.selectBonusSuit(bestSuit);
        System.out.println("Computer selects bonus suit: " + bestSuit);
    }

    // Decide which cards to swap
    public void swapCards() {
        String targetSuit = computer.getBonusSuit();
        Hand hand = computer.getHand();
        Card[] cards = hand.getCards();
        ArrayList<Integer> swapIndexes = new ArrayList<>();

        // Swap any card not in target suit
        for (int i = 0; i < cards.length; i++) {
            if (!cards[i].getSuit().equalsIgnoreCase(targetSuit)) {
                swapIndexes.add(i);
            }
        }

        // Limit to maximum 4 swaps
        int swaps = Math.min(swapIndexes.size(), 4);
        int[] indexesToSwap = new int[swaps];
        for (int i = 0; i < swaps; i++) {
            indexesToSwap[i] = swapIndexes.get(i);
        }

        // Perform swaps from deck
        computer.swapCards(indexesToSwap, deck);

        System.out.println("Computer swapped " + swaps + " cards.");
        System.out.println("Computer's new hand: " + computer.getHand());
    }
}
