import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieRatingAPI {

    private final String apiKey = "8e8f2632";

    // Gets movie information from the OMDb API
    public String getMovie(String movieTitle) throws IOException, InterruptedException {

        String url = "https://www.omdbapi.com/?t="
                + movieTitle.replace(" ", "+")
                + "&apikey=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // Gets the IMDb rating from the movie information
    public String getIMDbRating(String movieTitle) throws Exception {

        String movie = getMovie(movieTitle);

        int ratingStart = movie.indexOf("\"imdbRating\":\"");

        if (ratingStart == -1) {
            return "N/A";
        }

        ratingStart += 14;
        int ratingEnd = movie.indexOf("\"", ratingStart);

        return movie.substring(ratingStart, ratingEnd);
    }
    // Gets the Rotten Tomatoes rating from the movie information
    public String getRottenTomatoesRating(String movieTitle) throws Exception {

        String movie = getMovie(movieTitle);

        int sourceStart = movie.indexOf("\"Source\":\"Rotten Tomatoes\"");

        if (sourceStart == -1) {
            return "N/A";
        }

        int valueStart = movie.indexOf("\"Value\":\"", sourceStart);
        valueStart += 9;

        int valueEnd = movie.indexOf("\"", valueStart);

        return movie.substring(valueStart, valueEnd);
    }
    // Gets the Metacritic rating from the movie information
    public String getMetacriticRating(String movieTitle) throws Exception {

        String movie = getMovie(movieTitle);

        int sourceStart = movie.indexOf("\"Source\":\"Metacritic\"");

        if (sourceStart == -1) {
            return "N/A";
        }

        int valueStart = movie.indexOf("\"Value\":\"", sourceStart);
        valueStart += 9;

        int valueEnd = movie.indexOf("\"", valueStart);

        return movie.substring(valueStart, valueEnd);
    }
}
