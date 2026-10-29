
import java.util.List;

public class AverageRatingCalculator {

    public double calculateAverage(List<Double> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("No ratings available");
        }

        double sum = 0;

        for (double rating : ratings) {
            sum += rating;
        }

        return sum / ratings.size();
    }
}

