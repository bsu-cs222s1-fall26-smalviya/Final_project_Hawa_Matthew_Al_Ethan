
import java.util.List;

public class AverageRatingCalculator {

    // Calculates the average of all movie ratings
    public double calculateAverage(List<Double> ratings) {

        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("No ratings available");
        }

        double total = 0;

        for (double rating : ratings) {
            total += rating;
        }

        return total / ratings.size();
    }

    // Converts ratings from different websites
    public double convertRating(String rating) {

        if (rating == null || rating.equals("N/A")) {
            throw new IllegalArgumentException("Rating not available");
        }

        if (rating.endsWith("%")) {
            return Double.parseDouble(rating.replace("%", ""));
        }

        if (rating.contains("/")) {
            String[] numbers = rating.split("/");

            double score = Double.parseDouble(numbers[0]);
            double max = Double.parseDouble(numbers[1]);

            return score / max * 100;
        }

        // IMDb ratings are out of 10
        return Double.parseDouble(rating) * 10;
    }
}
