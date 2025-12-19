package cw2.project;

public class Player {

    private String name;
    private Hand hand;
    private String bonusSuit;
    private int totalScore;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.totalScore = 0;
    }

    // Accessors
    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getBonusSuit() {
        return bonusSuit;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addToTotalScore(int score) {
        this.totalScore += score;
    }

    // Choose bonus suit
    public void selectBonusSuit(String suit) {
        this.bonusSuit = suit;
    }

    // Swap cards in hand by indexes (indexes from 0 to 4)
    public void swapCards(int[] indexes, Deck deck) {
        for (int idx : indexes) {
            if (idx >= 0 && idx < 5) {
                Card newCard = deck.deal();
                if (newCard != null) {
                    hand.replaceCard(idx, newCard);
                }
            }
        }
    }

    // Calculate round score: highest suit total + 5 bonus if matches bonus suit
    public int calculateRoundScore() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int maxScore = 0;
        String bestSuit = "";

        for (String suit : suits) {
            int score = hand.getScoreForSuit(suit);
            if (score > maxScore) {
                maxScore = score;
                bestSuit = suit;
            }
        }

        if (bestSuit.equalsIgnoreCase(bonusSuit)) {
            maxScore += 5;
        }

        addToTotalScore(maxScore);
        return maxScore;
    }
}
