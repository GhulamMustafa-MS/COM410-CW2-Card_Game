package cw2.project;

import java.util.ArrayList;

public class Player {

    private String name;
    private Hand hand;
    private String bonusSuit;
    private int totalScore;

    // Replay history (stored per round)
    private ArrayList<String[]> initialHands = new ArrayList<>();   // 5 cards
    private ArrayList<String[]> finalHands = new ArrayList<>();     // 5 cards
    private ArrayList<String> bonusSuitsChosen = new ArrayList<>(); // "Hearts", etc.
    private ArrayList<String> maxSuitAtDeal = new ArrayList<>();    // best suit at start of round
    private ArrayList<Integer> maxScoreAtDeal = new ArrayList<>();  // best suit score at start
    private ArrayList<String[]> swapsMade = new ArrayList<>();      // lines like "A: old -> new"
    private ArrayList<Integer> roundScores = new ArrayList<>();     // final score each round

    public Player(String name) {
        this.name = name;
        this.totalScore = 0;
    }

    // --- Basic getters ---
    public String getName() { return name; }
    public Hand getHand() { return hand; }
    public String getBonusSuit() { return bonusSuit; }
    public int getTotalScore() { return totalScore; }

    private void addToTotalScore(int score) {
        this.totalScore += score;
    }

    // --- Replay helper: take snapshot of current hand (5 cards) ---
    private String[] snapshotHand() {
        String[] snap = new String[5];
        Card[] cards = hand.getCards();
        for (int i = 0; i < 5; i++) {
            snap[i] = cards[i].toString();
        }
        return snap;
    }

    // Set hand at start of turn and record initial hand + max possible suit score
    public void setHand(Hand hand) {
        this.hand = hand;

        // store initial hand snapshot
        initialHands.add(snapshotHand());

        // store "maximum possible from a single suit" at this moment
        String bestSuit = getHighestSuit();
        int bestScore = hand.getScoreForSuit(bestSuit);
        maxSuitAtDeal.add(bestSuit);
        maxScoreAtDeal.add(bestScore);
    }

    // Choose bonus suit and record for replay
    public void selectBonusSuit(String suit) {
        this.bonusSuit = suit;
        bonusSuitsChosen.add(suit);
    }

    // Swap cards and record swaps for replay
    public void swapCards(int[] indexes, Deck deck) {

        char[] labels = {'A', 'B', 'C', 'D', 'E'};
        ArrayList<String> swapLines = new ArrayList<>();

        for (int idx : indexes) {
            if (idx >= 0 && idx < 5) {
                Card oldCard = hand.getCards()[idx];
                Card newCard = deck.deal();
                if (newCard != null) {
                    hand.replaceCard(idx, newCard);
                    swapLines.add(labels[idx] + ": " + oldCard + "  ->  " + newCard);
                }
            }
        }

        // keep hand sorted after swaps
        hand.sortHand();

        // store swaps for this round
        swapsMade.add(swapLines.toArray(new String[0]));
    }

    // Used by GameLogic (and replay recording) to find highest suit at any moment
    public String getHighestSuit() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int max = -1;
        String best = suits[0];

        for (String s : suits) {
            int val = hand.getScoreForSuit(s);
            if (val > max) {
                max = val;
                best = s;
            }
        }
        return best;
    }

    // Calculate score for this round and store final hand snapshot for replay
    public int calculateRoundScore() {

        // Ensure swapsMade has an entry for this round even if no swaps were performed
        // (If the player pressed Enter, swapCards() might never be called)
        int roundIndex = roundScores.size();
        if (swapsMade.size() == roundIndex) {
            swapsMade.add(new String[0]); // no swaps
        }

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
        roundScores.add(maxScore);

        // store final hand snapshot
        finalHands.add(snapshotHand());

        return maxScore;
    }

    // --- Replay: more "game-like" formatting ---
    public void showReplay() {

        System.out.println("\n========================================");
        System.out.println("REPLAY: " + name);
        System.out.println("========================================");

        if (roundScores.isEmpty()) {
            System.out.println("No rounds played.");
            return;
        }

        char[] labels = {'A', 'B', 'C', 'D', 'E'};

        for (int r = 0; r < roundScores.size(); r++) {

            System.out.println("\n--------------------");
            System.out.println("ROUND " + (r + 1));
            System.out.println("--------------------");

            // 1) Show initial hand
            System.out.println("Initial hand:");
            String[] init = initialHands.get(r);
            for (int i = 0; i < 5; i++) {
                System.out.println("  " + labels[i] + ". " + init[i]);
            }

            // 2) Show maximum possible suit at deal time
            String bestSuit = (maxSuitAtDeal.size() > r ? maxSuitAtDeal.get(r) : "Unknown");
            int bestScore = (maxScoreAtDeal.size() > r ? maxScoreAtDeal.get(r) : 0);
            System.out.println("\nMax possible from a single suit: " + bestSuit + " = " + bestScore);

            // 3) Bonus suit chosen
            String bonus = (bonusSuitsChosen.size() > r ? bonusSuitsChosen.get(r) : "Unknown");
            System.out.println("Bonus suit chosen: " + bonus);

            // 4) Swaps made
            System.out.println("\nCard swaps:");
            String[] swaps = (swapsMade.size() > r ? swapsMade.get(r) : new String[0]);
            if (swaps.length == 0) {
                System.out.println("  No cards swapped.");
            } else {
                for (String s : swaps) {
                    System.out.println("  " + s);
                }
            }

            // 5) Final hand
            System.out.println("\nFinal hand:");
            String[] fin = (finalHands.size() > r ? finalHands.get(r) : new String[0]);
            if (fin.length == 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("  " + labels[i] + ". " + fin[i]);
                }
            } else {
                System.out.println("  (Final hand not recorded)");
            }

            // 6) Final score
            System.out.println("\nRound score: " + roundScores.get(r));
        }

        System.out.println("\n========================================");
        System.out.println("END OF REPLAY: " + name);
        System.out.println("========================================");
    }
}
