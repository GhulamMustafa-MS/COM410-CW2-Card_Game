package cw2.project;

import java.util.Scanner;

public class CW2Project {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("      Welcome to HighSuit");
        System.out.println("=================================\n");

        // Choose number of Players
        int numPlayers = readIntInRange(scanner, "Enter number of players (1 or 2): ", 1, 2);

        Player[] players = new Player[numPlayers];

        // Enter player names
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + " (type 'Computer' for AI): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = "Player" + (i + 1);
            players[i] = new Player(name);
        }

        // Choose number of rounds
        int rounds = readIntInRange(scanner, "Enter number of rounds (1 to 3): ", 1, 3);

        // Start the game
        GameLogic game = new GameLogic(players, rounds, scanner);
        game.playGame();

        // Save high scores (average score)
        Highscore highScore = new Highscore();
        for (Player player : players) {
            int avgScore = player.getTotalScore() / rounds;
            highScore.addScore(player.getName(), avgScore);
        }

        // Ask to display high scores
        System.out.print("\nWould you like to view the high score table? (Y/N): ");
        String viewScores = scanner.nextLine();
        if (viewScores.equalsIgnoreCase("Y")) {
            highScore.displayScores();
        }

        // Ask to display replay
        System.out.print("\nWould you like to view the replay? (Y/N): ");
        String replay = scanner.nextLine();
        if (replay.equalsIgnoreCase("Y")) {
            for (Player player : players) {
                player.showReplay();
            }
        }

        System.out.println("\nThank you for playing HighSuit!");
        scanner.close();
    }
        // Helper method made to simplify code where user needs to enter a number between 2 ranges
        // such as choosing number of players or rounds
    private static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a number from " + min + " to " + max + ".");
        }
    }
}
