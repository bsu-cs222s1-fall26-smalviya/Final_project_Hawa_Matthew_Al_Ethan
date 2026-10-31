
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AverageRatingCalculatorTest {

    // Tests the average of three movie ratings
    @Test
    public void testAverage() {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        List<Double> ratings = List.of(80.0, 90.0, 70.0);

        assertEquals(80.0, calculator.calculateAverage(ratings), 0.01);
    }

    // Tests ratings from different movie websites
    @Test
    public void testIMDbRating() {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        assertEquals(75.0, calculator.convertRating("7.5"), 0.01);
    }

    @Test
    public void testRottenTomatoesRating() {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        assertEquals(85.0, calculator.convertRating("85%"), 0.01);
    }

    @Test
    public void testMetacriticRating() {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        assertEquals(70.0, calculator.convertRating("70/100"), 0.01);
    }

    // Checks what happens when no ratings are given
    @Test
    public void testEmptyRatings() {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateAverage(List.of());
        });
    }

    // Tests the average rating using movie data from the API
    @Test
    public void testMovieAverage() throws Exception {
        AverageRatingCalculator calculator = new AverageRatingCalculator();

        double average = calculator.getMovieAverage("Shrek");

        assertTrue(average >= 0 && average <= 100);
    }

}

