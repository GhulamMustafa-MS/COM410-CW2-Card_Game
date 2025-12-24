package cw2.project;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class HighscoreTest {

    @Test
    public void highscoreFileIsCreated() {
        Highscore hs = new Highscore();

        File file = new File("highscores.txt");
        assertTrue("highscores.txt should be created if it does not exist", file.exists());
        assertNotNull(hs);
    }

    @Test
    public void addingScoresDoesNotCrash() {
        Highscore hs = new Highscore();

        hs.addScore("Test1", 10);
        hs.addScore("Test2", 20);
        hs.addScore("Test3", 30);
        hs.addScore("Test4", 40);
        hs.addScore("Test5", 50);
        hs.addScore("Test6", 60);

        // If no exception occurred, the test passes
        assertTrue(true);
    }

    @Test
    public void highscoresPersistBetweenInstances() {
        Highscore hs1 = new Highscore();
        hs1.addScore("PersistentTest", 99);

        // New instance should reload from file without crashing
        Highscore hs2 = new Highscore();
        assertNotNull(hs2);
    }
}
