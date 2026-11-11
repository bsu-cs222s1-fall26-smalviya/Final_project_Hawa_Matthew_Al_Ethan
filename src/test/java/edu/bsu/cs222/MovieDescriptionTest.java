package edu.bsu.cs222;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieDescriptionTest {

    @Test
    public void testMovieTitle() {
        MovieDescription movie = new MovieDescription(
                "Shrek", "An ogre goes on an adventure.");

        assertEquals("Shrek", movie.getTitle());
    }

    @Test
    public void testMovieDescription() {
        MovieDescription movie = new MovieDescription(
                "Shrek", "An ogre goes on an adventure.");

        assertEquals("An ogre goes on an adventure.",
                movie.getDescription());
    }

    @Test
    public void testDescriptionAvailable() {
        MovieDescription movie = new MovieDescription(
                "Shrek", "An ogre goes on an adventure.");

        assertTrue(movie.hasDescription());
    }

    @Test
    public void testMissingDescription() {
        MovieDescription movie = new MovieDescription(
                "Shrek", "N/A");

        assertFalse(movie.hasDescription());
    }
}

