
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
}
