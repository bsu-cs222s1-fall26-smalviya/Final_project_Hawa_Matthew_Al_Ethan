import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieRatingAPITest {

    @Test
    public void movieRatingsAPICanBeCreated() {
        MovieRatingAPI api = new MovieRatingAPI();

        assertNotNull(api);
    }
    @Test
    public void getsMovieInformation() throws Exception {
        MovieRatingAPI api = new MovieRatingAPI();

        String movie = api.getMovie("Shrek");

        assertTrue(movie.contains("Shrek"));
    }
}