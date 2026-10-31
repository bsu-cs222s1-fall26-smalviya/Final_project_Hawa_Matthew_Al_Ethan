import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieRatingAPI {

    private final String apiKey = "8e8f2632";

    public String getApiKey() {
        return apiKey;
    }
    public String getMovie(String movieTitle) throws IOException, InterruptedException {

        String movieURL = "https://www.omdbapi.com/?apikey="
                + apiKey
                + "&t="
                + movieTitle.replace(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(movieURL))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
}