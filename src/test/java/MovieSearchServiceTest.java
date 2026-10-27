
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieSearchServiceTest {

    MovieSearchService search = new MovieSearchService();

    @Test
    void testNormalMovieTitle() {
        assertEquals("Black+Panther",
                search.prepareSearch("Black Panther"));
    }

    @Test
    void testExtraSpaces() {
        assertEquals("Titanic",
                search.prepareSearch("  Titanic  "));
    }

    @Test
    void testEmptyTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> search.prepareSearch(""));
    }

    @Test
    void testNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> search.prepareSearch(null));
    }

    @Test
    void testMovieTitleWithNumbers() {
        assertEquals("Spider-Man+2",
                search.prepareSearch("Spider-Man 2"));
    }

    @Test
    void testMovieTitleWithSpecialCharacters() {
        assertEquals("Fast+%26+Furious",
                search.prepareSearch("Fast & Furious"));
    }

    @Test
    void testOnlySpaces() {
        assertThrows(IllegalArgumentException.class,
                () -> search.prepareSearch("   "));
    }

    @Test
    void testLowercaseMovieTitle() {
        assertEquals("avatar",
                search.prepareSearch("avatar"));
    }
}
