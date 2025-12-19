package cw2.project;

import java.io.*;
import java.util.*;

public class HighScore {

    private static final String FILE_NAME = "highscores.txt"; // file to store scores
    private List<Integer> scores;

    public HighScore() {
        scores = new ArrayList<>();
        loadScores();
    }

    // Load scores from file
    private void loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile(); // create file if it doesn't exist
            } catch (IOException e) {
                System.out.println("Error creating high score file: " + e.getMessage());
            }
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    int score = Integer.parseInt(line.trim());
                    scores.add(score);
                } catch (NumberFormatException ignored) {
                    // skip invalid lines
                }
            }
            Collections.sort(scores, Collections.reverseOrder()); // highest first
        } catch (FileNotFoundException e) {
            System.out.println("High score file not found: " + e.getMessage());
        }
    }

    // Add a new score and update the file if it makes top 5
    public void addScore(int score) {
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder()); // highest first
        if (scores.size() > 5) {
            scores = scores.subList(0, 5); // keep only top 5
        }
        saveScores();
    }

    // Save current scores to file
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int score : scores) {
                writer.println(score);
            }
        } catch (IOException e) {
            System.out.println("Error writing high score file: " + e.getMessage());
        }
    }

    // Display top 5 scores
    public void displayScores() {
        System.out.println("\n=== High Scores ===");
        if (scores.isEmpty()) {
            System.out.println("No high scores yet!");
            return;
        }
        for (int i = 0; i < scores.size(); i++) {
            System.out.println((i + 1) + ". " + scores.get(i));
        }
    }

    // Optional: get top score
    public int getTopScore() {
        return scores.isEmpty() ? 0 : scores.get(0);
    }
}
