
package edu.bsu.cs222;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieDescriptionTest {

    @Test
    public void testMovieDescription() throws Exception {
        MovieDescriptionAPI api = new MovieDescriptionAPI();

        String description = api.gatherMovieDescription("Shrek");

        assertNotNull(description);
        assertFalse(description.isBlank());
        assertNotEquals("N/A", description);
    }

    @Test
    public void testMissingMovieDescription() throws Exception {
        MovieDescriptionAPI api = new MovieDescriptionAPI();

        String description = api.gatherMovieDescription(
                "ThisMovieDoesNotExist123456");

        assertEquals("N/A", description);
    }
}
