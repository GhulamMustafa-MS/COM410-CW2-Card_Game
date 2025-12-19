package cw2.project;

import java.util.Scanner;

public class GameLogic {

    private Player[] players;
    private Deck deck;
    private int rounds;
    private Scanner scanner;

    // Constructor
    public GameLogic(Player[] players, int rounds) {
        this.players = players;
        this.rounds = rounds;
        this.deck = new Deck();
        this.scanner = new Scanner(System.in);
    }

    // Play the full game
    public void playGame() {
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n=== Round " + round + " ===");
            playRound();
        }

        displayFinalResults();
    }

    // Play a single round
    private void playRound() {
        // Deal hands to each player
        for (Player player : players) {
            player.setHand(new Hand(deck)); // New hand from deck
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }

        // Each player chooses bonus suit and swaps cards
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase("Computer")) {
                // Computer player logic
                ComputerLogic ai = new ComputerLogic(player, deck);
                ai.chooseBonusSuit();
                ai.swapCards();
            } else {
                // Human player chooses bonus suit
                System.out.print(player.getName() + ", choose your bonus suit (Clubs/Diamonds/Hearts/Spades): ");
                String suit = scanner.nextLine();
                player.selectBonusSuit(suit);

                // Human player can swap up to 4 cards
                System.out.println("\n" + player.getName() + "'s current hand: " + player.getHand());
                System.out.print("Enter card positions to swap (0-4, separated by space, max 4), or press Enter to keep all: ");
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    String[] parts = line.trim().split("\\s+");
                    int[] indexes = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        indexes[i] = Integer.parseInt(parts[i]);
                    }
                    player.swapCards(indexes, deck);
                    System.out.println("Updated hand: " + player.getHand());
                }
            }
        }

        // Calculate and display scores for this round
        for (Player player : players) {
            int roundScore = player.calculateRoundScore();
            System.out.println(player.getName() + "'s score this round: " + roundScore);
        }
    }

    // Display final results and winner
    private void displayFinalResults() {
        System.out.println("\n=== Final Scores ===");
        Player winner = players[0];
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
            if (player.getTotalScore() > winner.getTotalScore()) {
                winner = player;
            }
        }
        System.out.println("Winner: " + winner.getName() + "!");
    }
}
