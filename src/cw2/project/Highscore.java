package cw2.project;

import java.io.*;
import java.util.*;

public class Highscore {

    private static final String FILE_NAME = "highscores.txt";
    private ArrayList<ScoreEntry> scores;

    // Inner class to store name + score
    private static class ScoreEntry {
        String name;
        int score;

        ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    public Highscore() {
        scores = new ArrayList<>();
        loadScores();
    }

    // Load scores from file
    private void loadScores() {
        File file = new File(FILE_NAME);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(name, score));
                }
            }
            scanner.close();

            sortAndTrim();

        } catch (Exception e) {
            System.out.println("Error loading high scores.");
        }
    }

    // Add new score
    public void addScore(String name, int score) {
        scores.add(new ScoreEntry(name, score));
        sortAndTrim();
        saveScores();
    }

    // Sort scores and keep top 5
    private void sortAndTrim() {
        scores.sort((a, b) -> b.score - a.score);
        if (scores.size() > 5) {
            scores = new ArrayList<>(scores.subList(0, 5));
        }
    }

    // Save scores to file
    private void saveScores() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));
            for (ScoreEntry entry : scores) {
                writer.println(entry.name + "," + entry.score);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving high scores.");
        }
    }

    // Display scores
    public void displayScores() {
        System.out.println("\n=== High Scores ===");
        if (scores.isEmpty()) {
            System.out.println("No high scores yet.");
            return;
        }

        for (int i = 0; i < scores.size(); i++) {
            ScoreEntry s = scores.get(i);
            System.out.println((i + 1) + ". " + s.name + " - " + s.score);
        }
    }
}
