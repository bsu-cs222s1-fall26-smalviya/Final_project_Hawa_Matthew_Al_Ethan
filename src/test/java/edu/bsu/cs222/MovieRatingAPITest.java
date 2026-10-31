package edu.bsu.cs222;

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
    @Test
    public void getsIMDbRating() throws Exception {
        MovieRatingAPI api = new MovieRatingAPI();

        String rating = api.getIMDbRating("Shrek");

        System.out.println("IMDb rating: " + rating);
        assertTrue(rating.matches("\\d+(\\.\\d+)?"));
    }
    @Test
    public void getsRottenTomatoesRating() throws Exception {
        MovieRatingAPI api = new MovieRatingAPI();

        String rating = api.getRottenTomatoesRating("Shrek");

        System.out.println("Rotten Tomatoes: " + rating);
        assertTrue(rating.matches("\\d+%"));

    }
    @Test
    public void getsMetacriticRating() throws Exception {
        MovieRatingAPI api = new MovieRatingAPI();

        String rating = api.getMetacriticRating("Shrek");

        System.out.println("Metacritic: " + rating);
        assertTrue(rating.matches("\\d+/100"));

    }
}
