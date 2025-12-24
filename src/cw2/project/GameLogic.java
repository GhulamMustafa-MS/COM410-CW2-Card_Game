package cw2.project;

import java.util.Arrays;
import java.util.Scanner;

public class GameLogic {

    private Player[] players;
    private Deck deck;
    private int rounds;
    private Scanner scanner;

    public GameLogic(Player[] players, int rounds, Scanner scanner) {
        this.players = players;
        this.rounds = rounds;
        this.scanner = scanner;
    }

    // Play full game
    public void playGame() {

        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n==============================");
            System.out.println("        ROUND " + round);
            System.out.println("==============================");

            deck = new Deck(); // fresh deck each round

            for (Player player : players) {
                playTurn(player);
            }
        }

        displayFinalResults();
    }

    // One complete turn per player
    private void playTurn(Player player) {

        // Deal hand
        player.setHand(new Hand(deck));

        System.out.println("\n--------------------------------");
        System.out.println(player.getName() + "'s TURN");
        System.out.println("--------------------------------");

        // Display hand
        displayHand(player);

        // ✅ Requirement: display maximum score achievable from a single suit (current hand)
        String bestSuitNow = player.getHighestSuit();
        int bestSuitScoreNow = player.getHand().getScoreForSuit(bestSuitNow);
        System.out.println("Max score possible from a single suit right now: " + bestSuitNow + " = " + bestSuitScoreNow);

        boolean isComputer = player.getName().equalsIgnoreCase("Computer");

        // BONUS SUIT
        if (isComputer) {
            ComputerLogic ai = new ComputerLogic(player, deck);
            ai.chooseBonusSuit();
            ai.swapCards(); // use same AI instance
        } else {
            chooseBonusSuitHuman(player);
            swapCardsHuman(player);
        }

        // Final hand
        System.out.println("\nFinal hand:");
        displayHand(player);

        // Score
        int score = player.calculateRoundScore();
        System.out.println(player.getName() + "'s score this round: " + score);
    }

    // Display hand nicely A–E
    private void displayHand(Player player) {
        Card[] cards = player.getHand().getCards();
        char[] labels = {'A', 'B', 'C', 'D', 'E'};

        for (int i = 0; i < cards.length; i++) {
            System.out.println(labels[i] + ". " + cards[i]);
        }
    }

    // Choose the bonus suit
    private void chooseBonusSuitHuman(Player player) {

        while (true) {
            System.out.print("\nChoose bonus suit (C = Clubs, D = Diamonds, H = Hearts, S = Spades): ");
            String input = scanner.nextLine().trim().toUpperCase(); // This will make sure case does not matter

            switch (input) {
                case "C":
                case "CLUBS":
                    player.selectBonusSuit("Clubs");
                    return;
                case "D":
                case "DIAMONDS":
                    player.selectBonusSuit("Diamonds");
                    return;
                case "H":
                case "HEARTS":
                    player.selectBonusSuit("Hearts");
                    return;
                case "S":
                case "SPADES":
                    player.selectBonusSuit("Spades");
                    return;
                default:
                    System.out.println("Invalid input. Please enter C, D, H or S.");
            }
        }
    }

    // Human card swapping
    private void swapCardsHuman(Player player) {

        System.out.print("\nEnter letters of cards to swap (A to E, max 4, separated by spaces), or press Enter to keep all: ");

        String line = scanner.nextLine().trim().toUpperCase();
        if (line.isEmpty()) return;

        String[] parts = line.split("\\s+");
        if (parts.length > 4) {
            System.out.println("You can only swap up to 4 cards.");
            return;
        }

        int[] indexes = new int[parts.length];
        int count = 0;

        for (String p : parts) {
            if (p.isEmpty()) continue;
            char c = p.charAt(0);
            int idx = c - 'A';

            if (idx >= 0 && idx < 5) {
                indexes[count++] = idx;
            } else {
                System.out.println("Invalid card letter: " + c);
            }
        }

        if (count == 0) return;

        int[] finalIndexes = new int[count];
        System.arraycopy(indexes, 0, finalIndexes, 0, count);

        player.swapCards(finalIndexes, deck);
    }

    // Final results in descending order
    private void displayFinalResults() {

        System.out.println("\n==============================");
        System.out.println("        FINAL RESULTS");
        System.out.println("==============================");

        Player[] sorted = players.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(b.getTotalScore(), a.getTotalScore()));

        for (Player p : sorted) {
            System.out.println(p.getName() + ": " + p.getTotalScore());
        }

        System.out.println("\nWinner: " + sorted[0].getName());
    }
}
